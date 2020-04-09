package trial;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.google.gson.Gson;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TrialFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private static final Object[] tableheadings = new Object[] {"name", "quanitity", "expiration", "type"};
	String name = "";
	int aboutnum = 0;
	int survivor = 0;
	int numpeople = 1;
	private JTextField txtPeople;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrialFrame frame = new TrialFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TrialFrame() {
		setTitle("Covid-19 Stockpiler");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 32, 424, 150);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		DefaultTableModel model = new DefaultTableModel(tableheadings,0);
		table.setModel(model);
		
		JButton btnNewButton_1 = new JButton("Add Item");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.addRow(new Object[] {
					"Enter name", 0,"NA", "BasicItem"
				});
			}
		});
		btnNewButton_1.setBounds(109, 183, 89, 23);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveData("C:\\Users\\jansc\\Desktop\\CS\\text.json", model);
			}
		});
		btnNewButton.setBounds(109, 228, 89, 23);
		panel.add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton("Food");
		btnNewButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				name = "Food";
				model.fireTableDataChanged();
				
			}
		});
		btnNewButton_2.setBounds(10, 183, 89, 23);
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Drink");
		btnNewButton_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				name = "Drink";
				model.fireTableDataChanged();
				
			}
		});
		btnNewButton_3.setBounds(10, 228, 89, 23);
		panel.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Basic");
		btnNewButton_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				name = "BasicItem";
				model.fireTableDataChanged();
				
			}
		});
		btnNewButton_4.setBounds(10, 206, 89, 23);
		panel.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("Show All");
		btnNewButton_5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				name = "";
				model.fireTableDataChanged();
				
			}
		});
		btnNewButton_5.setBounds(300, 183, 89, 23);
		panel.add(btnNewButton_5);
		
		loadData("C:\\Users\\jansc\\Desktop\\CS\\text.json", model);
		
		TableRowSorter sorter = new TableRowSorter(model);
		table.setRowSorter(sorter);
		
		JButton btnNewButton_1_1 = new JButton("Del Item");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				model.removeRow(row);
			}	
		});
		btnNewButton_1_1.setBounds(109, 206, 89, 23);
		panel.add(btnNewButton_1_1);
		
		JLabel about = new JLabel("");
		about.setBounds(300, 228, 114, 23);
		panel.add(about);
		
		JButton btnNewButton_1_2 = new JButton("About");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(aboutnum%2 == 0) {
					about.setText("Jan 12/12/12 1.0.0");
					aboutnum += 1;
				}else {
					about.setText("");
					aboutnum += 1;
				}
			}
		});
		btnNewButton_1_2.setBounds(300, 206, 89, 23);
		panel.add(btnNewButton_1_2);
		
		JLabel survival = new JLabel("");
		survival.setBounds(198, 237, 99, 14);
		panel.add(survival);
		
		txtPeople = new JTextField();
		txtPeople.setText("# people");
		txtPeople.setBounds(208, 207, 86, 20);
		panel.add(txtPeople);
		txtPeople.setColumns(10);

		JButton btnNewButton_1_3 = new JButton("Survive:");
		btnNewButton_1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					survivor = (table.getRowCount()*20) / Integer.parseInt(txtPeople.getText());
					survival.setText(survivor + "days");
			}
		});
		btnNewButton_1_3.setBounds(208, 183, 89, 23);
		panel.add(btnNewButton_1_3);
		
		
		sorter.setRowFilter(new RowFilter() {
			@Override
			public boolean include(Entry entry) {
				String type = entry.getValue(3).toString();
				return type.startsWith(name);
			}
			
		});

	}
	
	void saveData(String filename, DefaultTableModel model) {
		Gson gson = new Gson();
		Vector dataVector = model.getDataVector();
		String textData = gson.toJson(dataVector);
		System.out.println(textData);
		
		Path path = Paths.get(filename);
		try {
			Files.writeString(path, textData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	void loadData(String filename, DefaultTableModel model) {
		Path path = Paths.get(filename);
		try {
			String textData = Files.readString(path);
			
			Gson gson = new Gson();
			Object[][] tableData = gson.fromJson(textData,Object[][].class); 
			model.setDataVector(tableData, tableheadings);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	void armaggedon() {
		
	}
}
