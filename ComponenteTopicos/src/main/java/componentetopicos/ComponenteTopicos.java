/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package componentetopicos;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;



public class ComponenteTopicos extends JComponent {

    private JComboBox<String> comboBox = new JComboBox<>();
    private String archivoCSV;
    private ArrayList<String> palabras;
    private int columnaBusqueda = 0; //Columna predeterminada
    // Constructor sin archivo por defecto
    public ComponenteTopicos (){
        this("");
    }
    //Constructor que recibe la ruta del archvo CSV
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
    // Metodo para cargar palabras desde el archivo CSV
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
    
    // Metodo setter para la ruta del archivo CSV
    public void setArchivoCSV(String archivoCSV){
        this.archivoCSV = archivoCSV;
        palabras = cargarPalabrasDesdeCSV(archivoCSV, columnaBusqueda);
        comboBox.removeAllItems();
        for (String palabra : palabras){
            comboBox.addItem(palabra);
        }
    }
    
    // Metodo getter para la ruta del archivo CSV
    public String getArchivoCSV(){
        return archivoCSV;
    }
    
    // Metodo setter para la columna de busqueda
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
    public int getColumnaBusqueda(){
        return columnaBusqueda;
    }
    public JComboBox<String> getComboBox(){
        return comboBox;
    }
    public Object getSeleccion(){
        return comboBox.getSelectedItem();
    }
    public void setIndex(int i){
        comboBox.setSelectedIndex(i);
    }
}

