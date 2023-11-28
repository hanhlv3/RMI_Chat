package client;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import my_interface.IClient;
import my_interface.Room;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;

import javax.swing.JSeparator;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;

public class ClientView extends JFrame {

	public JPanel contentPane;
	public JTextField txtMessage;
	public JList<String> listChatterOnline;
	
	public String message;
	public  JTextArea textArea;
	 
	public ClientImp clientImp;
	public String myName;
	private JTabbedPane tabbedPane;
	private JList listGroups;
	private JPanel panel_3;
	private JButton btnCreateGroup;
	private JButton btnEmoji;
	private JButton btnFile;
	

	public ClientView(ClientImp clientImp, String myName) {
		this.clientImp = clientImp;
		this.myName = myName;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 535, 339);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 145, 280);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		listChatterOnline = new JList<String>();
		listChatterOnline.setBorder(new TitledBorder(null, "Danh s\u00E1ch Online", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.add(listChatterOnline);
		listChatterOnline.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		listChatterOnline.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if (!e.getValueIsAdjusting()) {
					String clientSelected = listChatterOnline.getSelectedValue();
					try {
						addToChatter(clientSelected);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		
		panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		listGroups = new JList();
		listGroups.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Danh s\u00E1ch nh\u00F3m", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.add(listGroups);
		
		btnCreateGroup = new JButton("Tạo nhóm");
		btnCreateGroup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientImp.openCreateGroupView();
			}
		});
		panel_3.add(btnCreateGroup, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(165, 11, 346, 280);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 346, 249);
		panel_1.add(tabbedPane);
		
		txtMessage = new JTextField();
		txtMessage.setBounds(0, 260, 229, 20);
		panel_1.add(txtMessage);
		txtMessage.setColumns(10);
		
		JButton btnSend = new JButton("");
		btnSend.setBackground(new Color(255, 255, 255));
		btnSend.setIcon(new ImageIcon("D:\\Java\\RMI\\RMI_GUI\\resources\\img\\airplane (1).png"));
		btnSend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					sendMessage();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnSend.setBounds(301, 260, 45, 20);
		panel_1.add(btnSend);
		
		btnEmoji = new JButton("");
		btnEmoji.setIcon(new ImageIcon("D:\\Java\\RMI\\RMI_GUI\\resources\\img\\icons8-emoji-48 (1) (1).png"));
		btnEmoji.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openEmojiDialog();
			}
		});
		btnEmoji.setBackground(new Color(255, 255, 255));
		btnEmoji.setBounds(258, 260, 45, 20);
		panel_1.add(btnEmoji);
		
		btnFile = new JButton("");
		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sendFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnFile.setIcon(new ImageIcon("D:\\Java\\RMI\\RMI_GUI\\resources\\img\\fileIcon (1).png"));
		btnFile.setBackground(Color.WHITE);
		btnFile.setBounds(229, 260, 28, 23);
		panel_1.add(btnFile);
		
		this.setTitle(myName);
		this.setVisible(true);
	}

	protected void sendFile() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn file để gửi");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Files", "txt", "pdf", "doc");
	    fileChooser.setFileFilter(filter);
		int result = fileChooser.showDialog(null, "Chọn file");
		fileChooser.setVisible(true);

		if (result == JFileChooser.APPROVE_OPTION) {
			String fileName = fileChooser.getSelectedFile().getName();
			String filePath = fileChooser.getSelectedFile().getAbsolutePath();
			
			// Convert file to byte[]
			Path file = Paths.get(filePath);
			byte[] fileData = Files.readAllBytes(file);
			
			int selectedIndex = tabbedPane.getSelectedIndex();
			if (selectedIndex  == -1) return;
			
			String nameReciver = tabbedPane.getTitleAt(selectedIndex);
			
			clientImp.sendFile(nameReciver, fileData, fileName);
		}
		
	}

	protected void openEmojiDialog() {
		JDialog emojiDialog = new JDialog();
		Object[][] emojiMatrix = new Object[6][6];
		int emojiCode = 0x1F601;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++)
				emojiMatrix[i][j] = new String(Character.toChars(emojiCode++));
		}

		JTable emojiTable = new JTable();
		emojiTable.setModel(new DefaultTableModel(emojiMatrix, new String[] { "", "", "", "", "", "" }) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		emojiTable.setFont(new Font("Dialog", Font.PLAIN, 20));
		emojiTable.setShowGrid(false);
		emojiTable.setIntercellSpacing(new Dimension(0, 0));
		emojiTable.setRowHeight(30);
		emojiTable.getTableHeader().setVisible(false);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < emojiTable.getColumnCount(); i++) {
			emojiTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			emojiTable.getColumnModel().getColumn(i).setMaxWidth(30);
		}
		emojiTable.setCellSelectionEnabled(true);
		emojiTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		emojiTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtMessage.setText(txtMessage.getText() + emojiTable
						.getValueAt(emojiTable.rowAtPoint(e.getPoint()), emojiTable.columnAtPoint(e.getPoint())));
			}
		});

		emojiDialog.setContentPane(emojiTable);
		emojiDialog.setTitle("Chọn emoji");
		emojiDialog.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
		emojiDialog.pack();
		emojiDialog.setLocationRelativeTo(this);
		emojiDialog.setVisible(true);
		
	}

	protected void addToChatter(String clientSelected) throws RemoteException {
		
		if (this.clientImp.isChatter(clientSelected)) return; 
		if (!this.clientImp.addChatter(clientSelected)) {
			JOptionPane.showMessageDialog(null, clientSelected+ " Từ chối chat với bạn");
			return;
		}
		// add tab
		textArea = new JTextArea();
		tabbedPane.addTab(clientSelected, null, textArea, null);
		
	}

	protected void sendMessage() throws RemoteException {
		// TODO Auto-generated method stub
		int selectedIndex = tabbedPane.getSelectedIndex();
		if (selectedIndex  == -1) return;
		
		String msg = this.myName + ": " + txtMessage.getText().trim();
		
		String nameReciver = tabbedPane.getTitleAt(selectedIndex);
		JTextArea jTextArea = (JTextArea) tabbedPane.getComponentAt(selectedIndex);
		jTextArea.append(msg + "\n");
		txtMessage.setText("");
		
		// send toi retrive
		clientImp.sendMessage(nameReciver, msg);
		
	}

	public void setMessage(String nameSender, String msg) {
		JTextArea jArea = getTextAreaByTabName(nameSender);
		if (jArea != null ) {
			jArea.append(msg + "\n");
		}
	}
	
	public void setMessageFile(String nameSender, String fileName) {
		
	}
	
	private JTextArea getTextAreaByTabName(String tabName) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            String currentTabName = tabbedPane.getTitleAt(i);
            if (currentTabName.equals(tabName)) {
                return (JTextArea) tabbedPane.getComponentAt(i);
            }
        }
        return null; // Trả về null nếu không tìm thấy tab với tên cần tìm
    }

	public boolean notifyNewChatter(String nameChatter) throws RemoteException {

        // Hiển thị thông báo với tiêu đề và các tùy chọn
        int option = JOptionPane.showConfirmDialog(null, nameChatter + " muốn chat với bạn?", "Thông báo", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // add thêm vô chatter
        	addChatter(nameChatter);
        	return true;
        } else {
        
        }
        return false;
	}
	
	protected void addChatter(String nameChatter) throws RemoteException {
		this.clientImp.addChatter(nameChatter, 0);
		// add tab
		textArea = new JTextArea();
		tabbedPane.addTab(nameChatter, null, textArea, null);
	}

	public void updateGroupList(List<Room> listRooms) {
		Vector<String> roomNames = new Vector<String>();
		
		for (Room item : listRooms) {
			roomNames.add(item.getRoomName());
		}
		listGroups.setListData(roomNames);
	}

	public void insertTabChat(Room room) {
		// add tab
		textArea = new JTextArea();
		tabbedPane.addTab(room.getRoomName(), null, textArea, null);
	}
}
