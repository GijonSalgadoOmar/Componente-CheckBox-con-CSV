# Componente-CheckBox-con-CSV
Documentación de un componente CheckBox, el cual puede conectarse con un archivo csv para hacer una busqueda de datos

/**
 * ComponenteTopicos es una clase personalizada de JComponent que facilita la búsqueda y selección de palabras
 * desde un archivo CSV. El componente permite seleccionar en cuál columna del archivo realizar la búsqueda y
 * ofrece sugerencias en tiempo real mientras el usuario escribe en el campo de texto.
 * 
 * La clase permite:
 * - Leer datos de un archivo CSV.
 * - Filtrar resultados en tiempo real en base a la entrada del usuario.
 * - Seleccionar la columna de búsqueda del archivo CSV.
 * 
 * Ejemplo de uso:
 * <pre>
 *     ComponenteTopicos componente = new ComponenteTopicos("ruta/al/archivo.csv");
 *     componente.setColumnaBusqueda(1); // Especificar la columna de búsqueda
 * </pre>
 * 
 */
public class ComponenteTopicos extends JComponent {

    private JComboBox<String> comboBox = new JComboBox<>();
    private String archivoCSV;
    private ArrayList<String> palabras;
    private int columnaBusqueda = 0; // Columna predeterminada

    /**
     * Constructor por defecto. Crea un ComponenteTopicos sin cargar un archivo CSV.
     */
    public ComponenteTopicos() {
        this("");
    }

    /**
     * Constructor que recibe la ruta de un archivo CSV y carga las palabras desde la columna especificada.
     * 
     * @param archivoCSV La ruta del archivo CSV a cargar.
     */
    public ComponenteTopicos(String archivoCSV) {
        setLayout(new BorderLayout());

        // Inicializar ComboBox
        comboBox = new JComboBox<>();
        comboBox.setEditable(true);

        this.archivoCSV = archivoCSV;

        // Cargar palabras desde el archivo CSV si la ruta está especificada
        if (!archivoCSV.isEmpty()) {
            palabras = cargarPalabrasDesdeCSV(archivoCSV, columnaBusqueda);
            for (String palabra : palabras) {
                comboBox.addItem(palabra);
            }
        }
        add(comboBox, BorderLayout.CENTER);

        // Agregar funcionalidad de búsqueda
        JTextField textField = (JTextField) comboBox.getEditor().getEditorComponent();
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = textField.getText();
                comboBox.removeAllItems();

                for (String palabra : palabras) {
                    if (palabra.toLowerCase().contains(text.toLowerCase())) {
                        comboBox.addItem(palabra);
                    }
                }

                if (comboBox.getItemCount() == 0 && text.isEmpty()) {
                    for (String palabra : palabras) {
                        comboBox.addItem(palabra);
                    }
                }

                textField.setText(text);
                comboBox.showPopup();
            }
        });
    }

    /**
     * Carga palabras desde una columna específica del archivo CSV.
     * 
     * @param archivo La ruta del archivo CSV.
     * @param columna El índice de la columna desde la cual se extraerán las palabras.
     * @return Una lista de palabras cargadas desde la columna especificada.
     */
    private ArrayList<String> cargarPalabrasDesdeCSV(String archivo, int columna) {
        ArrayList<String> listaPalabras = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] columnas = linea.split(","); // Separador de columnas CSV
                if (columna < columnas.length) {
                    listaPalabras.add(columnas[columna].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaPalabras;
    }

    /**
     * Establece la ruta del archivo CSV y carga las palabras desde el archivo especificado.
     * 
     * @param archivoCSV La ruta del archivo CSV.
     */
    public void setArchivoCSV(String archivoCSV) {
        this.archivoCSV = archivoCSV;
        palabras = cargarPalabrasDesdeCSV(archivoCSV, columnaBusqueda);
        comboBox.removeAllItems();
        for (String palabra : palabras) {
            comboBox.addItem(palabra);
        }
    }

    /**
     * Obtiene la ruta del archivo CSV actualmente cargado.
     * 
     * @return La ruta del archivo CSV.
     */
    public String getArchivoCSV() {
        return archivoCSV;
    }

    /**
     * Establece el índice de la columna en la que se realizará la búsqueda.
     * 
     * @param columna El índice de la columna de búsqueda.
     */
    public void setColumnaBusqueda(int columna) {
        this.columnaBusqueda = columna;
        if (!archivoCSV.isEmpty()) {
            palabras = cargarPalabrasDesdeCSV(archivoCSV, columnaBusqueda);
            comboBox.removeAllItems();
            for (String palabra : palabras) {
                comboBox.addItem(palabra);
            }
        }
    }

    /**
     * Obtiene el índice de la columna en la que se está realizando la búsqueda.
     * 
     * @return El índice de la columna de búsqueda.
     */
    public int getColumnaBusqueda() {
        return columnaBusqueda;
    }

    /**
     * Devuelve el JComboBox del componente para facilitar el acceso y la manipulación adicional.
     * 
     * @return El JComboBox utilizado en este componente.
     */
    public JComboBox<String> getComboBox() {
        return comboBox;
    }

    /**
     * Obtiene el elemento seleccionado actualmente en el ComboBox de búsqueda.
     * 
     * @return El objeto actualmente seleccionado.
     */
    public Object getSeleccion() {
        return comboBox.getSelectedItem();
    }

    /**
     * Establece el índice de selección en el ComboBox de búsqueda.
     * 
     * @param i El índice que se debe seleccionar.
     */
    public void setIndex(int i) {
        comboBox.setSelectedIndex(i);
    }
}
