# Componente-CheckBox-con-CSV
Documentación de un componente CheckBox, el cual puede conectarse con un archivo csv para hacer una busqueda de datos.


ComponenteTopicos es una clase personalizada de JComponent que facilita la búsqueda y selección de palabras
   desde un archivo CSV. El componente permite seleccionar en cuál columna del archivo realizar la búsqueda y
   ofrece sugerencias en tiempo real mientras el usuario escribe en el campo de texto.
   
La clase permite:
 * Leer datos de un archivo CSV.
 * Filtrar resultados en tiempo real en base a la entrada del usuario.
 * Seleccionar la columna de búsqueda del archivo CSV.

  # Constructores:
* ComponenteTopicos() crea una instancia sin cargar un archivo.
  <pre>
      public ComponenteTopicos (){
        this("");
    }
  </pre>
* ComponenteTopicos(String archivoCSV) permite cargar el archivo CSV y cargar datos iniciales desde una columna.
<pre>
    public ComponenteTopicos(String archivoCSV){
        setLayout(new BorderLayout());
        //Inicializar ComboBox
        comboBox = new JComboBox<>();
        comboBox.setEditable(true);
        this.archivoCSV = archivoCSV;
        // Cargar las palabras del archivo si se especifica
        if(!archivoCSV.isEmpty()){
            palabras = cargarPalabrasDesdeCSV(archivoCSV, columnaBusqueda);
            for (String palabra : palabras){
                comboBox.addItem(palabra);   
            }    
        }
        add(comboBox, BorderLayout.CENTER);
        // Funcionalidad de busqueda
        JTextField textField = (JTextField) comboBox.getEditor().getEditorComponent();
        textField.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e){
                String text = textField.getText();
                comboBox.removeAllItems();
                for(String palabra : palabras){
                    if (palabra.toLowerCase().contains(text.toLowerCase())){
                        comboBox.addItem(palabra);
                    }
                }
                if (comboBox.getItemCount() == 0 && text.isEmpty()){
                    for (String palabra : palabras){
                        comboBox.addItem(palabra);
                    }
                }
                textField.setText(text);
                comboBox.showPopup();
            }
        });
    }
</pre>
  # Métodos Privados:
* cargarPalabrasDesdeCSV(String archivo, int columna): carga las palabras de una columna específica del archivo CSV.
  <pre>
      private ArrayList<String> cargarPalabrasDesdeCSV(String archivo, int columna){
        ArrayList<String> listaPalabras = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] columnas = linea.split(","); // Separador de columnas CSV
                if(columna< columnas.length){
                listaPalabras.add(columnas[columna].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaPalabras;
    }
  </pre>
# Métodos Públicos:
* setArchivoCSV(String archivoCSV): establece la ruta del archivo CSV.
  <pre>
      public void setArchivoCSV(String archivoCSV){
        this.archivoCSV = archivoCSV;
        palabras = cargarPalabrasDesdeCSV(archivoCSV, columnaBusqueda);
        comboBox.removeAllItems();
        for (String palabra : palabras){
            comboBox.addItem(palabra);
        }
    }
  </pre>
* getArchivoCSV(): obtiene la ruta del archivo actual.
* <pre>
    public String getArchivoCSV(){
        return archivoCSV;
    }
</pre>
* setColumnaBusqueda(int columna): define la columna de búsqueda.
<pre>
    public void setColumnaBusqueda(int columna){
        this.columnaBusqueda = columna;
        if (!archivoCSV.isEmpty()){
            palabras = cargarPalabrasDesdeCSV(archivoCSV, columnaBusqueda);
            comboBox.removeAllItems();
            for (String palabra : palabras){
                comboBox.addItem(palabra);
            }
        }
    }
</pre>
* getColumnaBusqueda(): obtiene el índice de la columna de búsqueda.
<pre>
    public int getColumnaBusqueda(){
        return columnaBusqueda;
    }
</pre>
* getComboBox(): proporciona acceso al JComboBox interno.
<pre>
    public JComboBox<String> getComboBox(){
        return comboBox;
    }
</pre>
* getSeleccion(): obtiene el elemento seleccionado.
        <pre>
            public Object getSeleccion(){
        return comboBox.getSelectedItem();
    }
        </pre>
* setIndex(int i): establece la selección del índice en el ComboBox.
<pre>
    public void setIndex(int i){
        comboBox.setSelectedIndex(i);
    }
</pre>
