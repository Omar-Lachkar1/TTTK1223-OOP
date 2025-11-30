package LMS_BetaV1;
import javax.swing.*;
import javax.swing.event.TableModelEvent; // NEW IMPORT
import javax.swing.event.TableModelListener; // NEW IMPORT
import javax.swing.table.DefaultTableModel; // New Import

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class Admin_Menu {
    public static void admin_menu() {
        // Create fullscreen window
        JFrame f = new JFrame("Admin Dashboard - Library Management System");
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Main panel with background image
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    ImageIcon imageIcon = new ImageIcon("library.jpg");
                    Image image = imageIcon.getImage();
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    g.setColor(new Color(70, 130, 180));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        
        // --- MODIFIED: Header and Control Panel (Includes Logout) ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        // Header
        JLabel headerLabel = new JLabel("ADMIN DASHBOARD", JLabel.CENTER);
        headerLabel.setFont(new Font("Bahnschrift", Font.BOLD, 48));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        topPanel.add(headerLabel, BorderLayout.CENTER);
        
        // Logout Button (Top Right)
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        controlPanel.setOpaque(false);
        
        JButton logout_but = new JButton("Logout");
        styleControlButton(logout_but, new Color(220, 20, 60)); // Crimson for Logout
        logout_but.addActionListener(e -> {
            f.dispose();
            
            Login.login(); // Go back to login screen
        });
        controlPanel.add(logout_but);
        topPanel.add(controlPanel, BorderLayout.EAST);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
     
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 30, 30));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        
        // Create buttons
        JButton view_but = createMenuButton("VIEW BOOKS");
        JButton users_but = createMenuButton("VIEW USERS");
        JButton issued_but = createMenuButton("VIEW ISSUED BOOKS");
        JButton add_user = createMenuButton("ADD USER");
        JButton add_book = createMenuButton("ADD BOOK");
        JButton issue_book = createMenuButton("ISSUE BOOK");
        JButton return_book = createMenuButton("RETURN BOOK");
        JButton create_but = createDelButton("RESET DATABASE");
        JButton del_user = createDelButton("DELETE USER");
        JButton del_book = createDelButton("DELETE BOOK");
        
        // Add action listeners
        
        view_but.addActionListener(e -> showBooksWindow());
        users_but.addActionListener(e -> showUsersWindow());
        issued_but.addActionListener(e -> showIssuedBooksWindow());
        add_user.addActionListener(e -> showAddUserWindow());
        add_book.addActionListener(e -> showAddBookWindow());
        issue_book.addActionListener(e -> showIssueBookWindow());
        return_book.addActionListener(e -> showReturnBookWindow());
        del_user.addActionListener(e -> showDeleteUserWindow());
        del_book.addActionListener(e -> showDeleteBookWindow());
        create_but.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to reset the database? This cannot be undone!", 
                "Confirm Reset", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    FileHandler.resetDatabase();
                    JOptionPane.showMessageDialog(null, "Database reset successfully!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error resetting database: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Add buttons to panel
        buttonPanel.add(view_but);
        buttonPanel.add(users_but);
        buttonPanel.add(issued_but);
        buttonPanel.add(add_user);
        buttonPanel.add(add_book);
        buttonPanel.add(issue_book);
        buttonPanel.add(return_book);
        buttonPanel.add(create_but);
        buttonPanel.add(del_user);
        buttonPanel.add(del_book);
        
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Footer
        JLabel footerLabel = new JLabel("Administrator Panel | © 2025 Library Management System", JLabel.CENTER);
        footerLabel.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(footerLabel, BorderLayout.SOUTH);
        
        f.add(mainPanel);
        f.setVisible(true);
    }
    
    private static void showDeleteUserWindow() {
        JFrame deleteFrame = new JFrame("Delete User");
        deleteFrame.setSize(600, 300);
        deleteFrame.setLocationRelativeTo(null);
        deleteFrame.setLayout(new BorderLayout());
        
        JPanel backgroundPanel = createBackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        
        // Add Back Button + Title Panel to NORTH
        backgroundPanel.add(createSubMenuTopPanel(deleteFrame, "DELETE USER"), BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel uidLabel = new JLabel("User ID:");
        uidLabel.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        uidLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        mainPanel.add(uidLabel, gbc);
        
        JTextField uidField = new JTextField(15);
        uidField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
        gbc.gridx = 1;
        mainPanel.add(uidField, gbc);
        
        JButton deleteButton = new JButton("DELETE USER");
        deleteButton.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        deleteButton.setBackground(new Color(220, 20, 60)); // Red color for delete
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mainPanel.add(deleteButton, gbc);
        
        deleteButton.addActionListener(e -> {
            String uidStr = uidField.getText();
            
            if (uidStr.isEmpty()) {
                JOptionPane.showMessageDialog(deleteFrame, "Please enter User ID!");
                return;
            }
            
            try {
                int uid = Integer.parseInt(uidStr);
                int confirm = JOptionPane.showConfirmDialog(deleteFrame, 
                    "Are you sure you want to delete user ID " + uid + "?\nThis action cannot be undone!", 
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = FileHandler.deleteUser(uid);
                    if (success) {
                        JOptionPane.showMessageDialog(deleteFrame, "User deleted successfully!");
                        deleteFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(deleteFrame, "User not found or cannot be deleted!");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(deleteFrame, "Please enter a valid User ID!");
            }
        });
        
        backgroundPanel.add(mainPanel, BorderLayout.CENTER);
        deleteFrame.add(backgroundPanel);
        deleteFrame.setVisible(true);
    }

    private static void showDeleteBookWindow() {
        JFrame deleteFrame = new JFrame("Delete Book");
        deleteFrame.setSize(600, 300);
        deleteFrame.setLocationRelativeTo(null);
        deleteFrame.setLayout(new BorderLayout());
        
        JPanel backgroundPanel = createBackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        
        // Add Back Button + Title Panel to NORTH
        backgroundPanel.add(createSubMenuTopPanel(deleteFrame, "DELETE BOOK"), BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel bidLabel = new JLabel("Book ID:");
        bidLabel.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        bidLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        mainPanel.add(bidLabel, gbc);
        
        JTextField bidField = new JTextField(15);
        bidField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
        gbc.gridx = 1;
        mainPanel.add(bidField, gbc);
        
        JButton deleteButton = new JButton("DELETE BOOK");
        deleteButton.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        deleteButton.setBackground(new Color(220, 20, 60)); // Red color for delete
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mainPanel.add(deleteButton, gbc);
        
        deleteButton.addActionListener(e -> {
            String bidStr = bidField.getText();
            
            if (bidStr.isEmpty()) {
                JOptionPane.showMessageDialog(deleteFrame, "Please enter Book ID!");
                return;
            }
            
            try {
                int bid = Integer.parseInt(bidStr);
                int confirm = JOptionPane.showConfirmDialog(deleteFrame, 
                    "Are you sure you want to delete book ID " + bid + "?\nThis action cannot be undone!", 
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = FileHandler.deleteBook(bid);
                    if (success) {
                        JOptionPane.showMessageDialog(deleteFrame, "Book deleted successfully!");
                        deleteFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(deleteFrame, "Book not found or cannot be deleted!");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(deleteFrame, "Please enter a valid Book ID!");
            }
        });
        
        backgroundPanel.add(mainPanel, BorderLayout.CENTER);
        deleteFrame.add(backgroundPanel);
        deleteFrame.setVisible(true);
    }
    
    private static JButton createDelButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Bahnschrift", Font.BOLD, 20));
        button.setBackground(new Color(255, 0, 0));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(250, 80));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        return button;
    }
    
    private static JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Bahnschrift", Font.BOLD, 20));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(250, 80));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        return button;
    }
    
    // MODIFIED METHOD TO ALLOW EDITING BOOKS
    private static void showBooksWindow() {
        JFrame booksFrame = new JFrame("All Books");
        booksFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        JPanel mainPanel = createBackgroundPanel();
        
        // --- MODIFIED: Replaced title with Back Button + Title Panel ---
        mainPanel.add(createSubMenuTopPanel(booksFrame, "ALL BOOKS IN LIBRARY (Click to Edit)"), BorderLayout.NORTH);
        
        List<String[]> books = FileHandler.getAllBooks();
        String[] columnNames = {"Book ID", "Book Name", "Genre", "Price (QAR)"};
        Object[][] data = new Object[books.size()][4];
        
        for (int i = 0; i < books.size(); i++) {
            String[] book = books.get(i);
            data[i][0] = book[0];
            data[i][1] = book[1];
            data[i][2] = book[2];
            data[i][3] = book[3]; // Keep as number string for editing
        }
        
        // --- START CUSTOM EDITABLE MODEL FOR BOOKS ---
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Allow editing for columns 1 (Name), 2 (Genre), 3 (Price).
                // Column 0 (Book ID) is NOT editable.
                return column >= 1 && column <= 3;
            }
            
            @Override
            public Class getColumnClass(int column) {
                // Ensures correct rendering/editing for all types
                return getValueAt(0, column).getClass();
            }
        };

        JTable table = new JTable(model);
        
        // Add a listener to save changes when a cell edit is complete
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    
                    if (column >= 1 && column <= 3) {
                        try {
                            String bookId = model.getValueAt(row, 0).toString();
                            String newValue = model.getValueAt(row, column).toString().trim();
                            
                            // Validate price before saving
                            if (column == 3) {
                                int price = Integer.parseInt(newValue);
                                if (price <= 0) {
                                    JOptionPane.showMessageDialog(booksFrame, "Price must be a positive number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                                    // Revert the cell to the original value if available, or just stop
                                    // For simplicity here, we'll just stop the save.
                                    return; 
                                }
                            }
                            
                            // Call the new FileHandler method to save the change
                            FileHandler.updateBookDetails(bookId, column, newValue);
                            JOptionPane.showMessageDialog(booksFrame, "Book ID " + bookId + " updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(booksFrame, "Price must be a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            // To prevent the error from showing multiple times on rapid edits,
                            // we would ideally revert the cell value here, but that is complex.
                        } catch (Exception ex) {
                             JOptionPane.showMessageDialog(booksFrame, "Error saving change: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        // --- END CUSTOM EDITABLE MODEL FOR BOOKS ---

        table.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Bahnschrift", Font.BOLD, 18));
        table.setBackground(new Color(255, 255, 255, 220));
        
        JScrollPane scrollPane = new JScrollPane(table);
        styleScrollPane(scrollPane);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        booksFrame.add(mainPanel);
        booksFrame.setVisible(true);
    }
    
    private static void showUsersWindow() {
        JFrame usersFrame = new JFrame("All Users");
        usersFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        JPanel mainPanel = createBackgroundPanel();
        
        // --- MODIFIED: Replaced title with Back Button + Title Panel ---
        mainPanel.add(createSubMenuTopPanel(usersFrame, "ALL SYSTEM USERS"), BorderLayout.NORTH);
        
        // Read users from file
        java.util.List<String> users = readUsersFromFile();
        String[] columnNames = {"User ID", "Username", "Password", "Admin Status"};
        Object[][] data = new Object[users.size()][4];
        
        for (int i = 0; i < users.size(); i++) {
            String[] userData = users.get(i).split(",");
            data[i][0] = userData[0];
            data[i][1] = userData[1];
            data[i][2] = userData[2];
            data[i][3] = userData[3].equals("1") ? "Administrator" : "User";
        }
        
        JTable table = createStyledTable(data, columnNames); // Uses non-editable helper
        JScrollPane scrollPane = new JScrollPane(table);
        styleScrollPane(scrollPane);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        usersFrame.add(mainPanel);
        usersFrame.setVisible(true);
    }
    
    private static java.util.List<String> readUsersFromFile() {
        java.util.List<String> users = new java.util.ArrayList<>();
        try {
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("users.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    
    private static void showIssuedBooksWindow() {
        JFrame issuedFrame = new JFrame("Issued Books");
        issuedFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        JPanel mainPanel = createBackgroundPanel();
        
        // --- MODIFIED: Replaced title with Back Button + Title Panel ---
        mainPanel.add(createSubMenuTopPanel(issuedFrame, "ALL ISSUED BOOKS"), BorderLayout.NORTH);
        
        List<String[]> issuedBooks = FileHandler.getAllIssuedBooks();
        String[] columnNames = {"Issue ID", "User ID", "Book ID", "Issue Date", "Return Date", "Period", "Fine (QAR)"};
        Object[][] data = new Object[issuedBooks.size()][7];
        
        for (int i = 0; i < issuedBooks.size(); i++) {
            String[] issue = issuedBooks.get(i);
            data[i][0] = issue[0];
            data[i][1] = issue[1];
            data[i][2] = issue[2];
            data[i][3] = issue[3];
            data[i][4] = issue[4].isEmpty() ? "Not Returned" : issue[4];
            data[i][5] = issue[6];
            data[i][6] = "QAR " + issue[7];
        }
        
        JTable table = createStyledTable(data, columnNames); // Uses non-editable helper
        JScrollPane scrollPane = new JScrollPane(table);
        styleScrollPane(scrollPane);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        issuedFrame.add(mainPanel);
        issuedFrame.setVisible(true);
    }
    
    private static void showAddUserWindow() {
        JFrame g = new JFrame("Add New User");
        g.setSize(600, 400);
        g.setLayout(new BorderLayout()); // Added layout for BorderLayout structure
        g.setLocationRelativeTo(null);
        
        
        JPanel backgroundPanel = createBackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(createSubMenuTopPanel(g, "ADD NEW USER"), BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new GridBagLayout());
        
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Removed original title label as it is now in the top panel
        
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        userLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(userLabel, gbc);
        
        JTextField userField = new JTextField(20);
        userField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
        gbc.gridx = 1;
        mainPanel.add(userField, gbc);
        
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        passLabel.setForeground(Color.WHITE);
        gbc.gridy = 2;
        gbc.gridx = 0;
        mainPanel.add(passLabel, gbc);
        
        JPasswordField passField = new JPasswordField(20);
        passField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
        gbc.gridx = 1;
        mainPanel.add(passField, gbc);
        
        JRadioButton adminRadio = new JRadioButton("Administrator");
        adminRadio.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
        adminRadio.setForeground(Color.WHITE);
        adminRadio.setOpaque(false);
        
        JRadioButton userRadio = new JRadioButton("Regular User");
        userRadio.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
        userRadio.setForeground(Color.WHITE);
        userRadio.setOpaque(false);
        userRadio.setSelected(true);
        
        ButtonGroup bg = new ButtonGroup();
        bg.add(adminRadio);
        bg.add(userRadio);
        
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JPanel radioPanel = new JPanel(new FlowLayout());
        radioPanel.setOpaque(false);
        radioPanel.add(adminRadio);
        radioPanel.add(userRadio);
        mainPanel.add(radioPanel, gbc);
        
        JButton createButton = new JButton("CREATE USER");
        createButton.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        createButton.setBackground(new Color(0, 102, 204));
        createButton.setForeground(Color.WHITE);
        gbc.gridy = 4;
        mainPanel.add(createButton, gbc);
        
        createButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            boolean isAdmin = adminRadio.isSelected();
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(g, "Please fill all fields!");
                return;
            }
            
            FileHandler.addUser(username, password, isAdmin);
            JOptionPane.showMessageDialog(g, "User added successfully!");
            g.dispose();
        });
        
        backgroundPanel.add(mainPanel, BorderLayout.CENTER);
        g.setContentPane(backgroundPanel);
        g.setVisible(true);
    }
    
    private static void showAddBookWindow() {
        JFrame g = new JFrame("Add New Book");
        g.setSize(600, 400);
        g.setLayout(new BorderLayout()); // Added layout for BorderLayout structure
        g.setLocationRelativeTo(null);
        
        // --- MODIFIED: Added Back Button + Title Panel ---
        g.add(createSubMenuTopPanel(g, "ADD NEW BOOK"), BorderLayout.NORTH);

        JPanel backgroundPanel = createBackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(createSubMenuTopPanel(g, "ADD NEW BOOK"), BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Removed original title label as it is now in the top panel

        JLabel nameLabel = new JLabel("Book Name:");
        nameLabel.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(nameLabel, gbc);
        
        JTextField nameField = new JTextField(20);
        nameField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
        gbc.gridx = 1;
        mainPanel.add(nameField, gbc);
        
        JLabel genreLabel = new JLabel("Genre:");
        genreLabel.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        genreLabel.setForeground(Color.WHITE);
        gbc.gridy = 2;
        gbc.gridx = 0;
        mainPanel.add(genreLabel, gbc);
        
        JTextField genreField = new JTextField(20);
        genreField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
        gbc.gridx = 1;
        mainPanel.add(genreField, gbc);
        
        JLabel priceLabel = new JLabel("Price (QAR):");
        priceLabel.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        priceLabel.setForeground(Color.WHITE);
        gbc.gridy = 3;
        gbc.gridx = 0;
        mainPanel.add(priceLabel, gbc);
        
        JTextField priceField = new JTextField(20);
        priceField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
        gbc.gridx = 1;
        mainPanel.add(priceField, gbc);
        
        JButton addButton = new JButton("ADD BOOK");
        addButton.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        addButton.setBackground(new Color(0, 102, 204));
        addButton.setForeground(Color.WHITE);
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mainPanel.add(addButton, gbc);
        
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String genre = genreField.getText();
            String priceStr = priceField.getText();
            
            if (name.isEmpty() || genre.isEmpty() || priceStr.isEmpty()) {
                JOptionPane.showMessageDialog(g, "Please fill all fields!");
                return;
            }
            
            try {
                int price = Integer.parseInt(priceStr);
                FileHandler.addBook(name, genre, price);
                JOptionPane.showMessageDialog(g, "Book added successfully!");
                g.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(g, "Please enter a valid price!");
            }
        });
        
        backgroundPanel.add(mainPanel, BorderLayout.CENTER);
        g.setContentPane(backgroundPanel);
        g.setVisible(true);
    }
    
    private static void showIssueBookWindow() {
        JFrame g = new JFrame("Issue Book");
        g.setSize(600, 500);
        g.setLayout(new BorderLayout()); // Added layout for BorderLayout structure
        g.setLocationRelativeTo(null);

        JPanel backgroundPanel = createBackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(createSubMenuTopPanel(g, "ISSUE BOOK TO USER"), BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Removed original title label as it is now in the top panel
        
        JLabel uidLabel = new JLabel("User ID:");
        uidLabel.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        uidLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(uidLabel, gbc);
        
        JTextField uidField = new JTextField(20);
        uidField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
        gbc.gridx = 1;
        mainPanel.add(uidField, gbc);
        
        JLabel bidLabel = new JLabel("Book ID:");
        bidLabel.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        bidLabel.setForeground(Color.WHITE);
        gbc.gridy = 2;
        gbc.gridx = 0;
        mainPanel.add(bidLabel, gbc);
        
        JTextField bidField = new JTextField(20);
        bidField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
        gbc.gridx = 1;
        mainPanel.add(bidField, gbc);
        
        JLabel periodLabel = new JLabel("Period (days):");
        periodLabel.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        periodLabel.setForeground(Color.WHITE);
        gbc.gridy = 3;
        gbc.gridx = 0;
        mainPanel.add(periodLabel, gbc);
        
        JTextField periodField = new JTextField(20);
        periodField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
        gbc.gridx = 1;
        mainPanel.add(periodField, gbc);
        
        JLabel dateLabel = new JLabel("Issue Date (DD-MM-YYYY):");
        dateLabel.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        dateLabel.setForeground(Color.WHITE);
        gbc.gridy = 4;
        gbc.gridx = 0;
        mainPanel.add(dateLabel, gbc);
        
        JTextField dateField = new JTextField(20);
        dateField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
        gbc.gridx = 1;
        mainPanel.add(dateField, gbc);
        
        JButton issueButton = new JButton("ISSUE BOOK");
        issueButton.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        issueButton.setBackground(new Color(0, 102, 204));
        issueButton.setForeground(Color.WHITE);
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mainPanel.add(issueButton, gbc);
        
        issueButton.addActionListener(e -> {
            String uidStr = uidField.getText();
            String bidStr = bidField.getText();
            String periodStr = periodField.getText();
            String date = dateField.getText();
            
            if (uidStr.isEmpty() || bidStr.isEmpty() || periodStr.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(g, "Please fill all fields!");
                return;
            }
            
            try {
                int uid = Integer.parseInt(uidStr);
                int bid = Integer.parseInt(bidStr);
                int period = Integer.parseInt(periodStr);
                
                FileHandler.issueBook(uid, bid, date, period);
                JOptionPane.showMessageDialog(g, "Book issued successfully!");
                g.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(g, "Please enter valid numbers!");
            }
        });
        
        backgroundPanel.add(mainPanel, BorderLayout.CENTER);
        g.setContentPane(backgroundPanel);
        g.setVisible(true);
    }
    
    private static void showReturnBookWindow() {
        JFrame g = new JFrame("Return Book");
        g.setSize(600, 300);
        g.setLayout(new BorderLayout()); // Added layout for BorderLayout structure
        g.setLocationRelativeTo(null);
        
        // --- MODIFIED: Added Back Button + Title Panel ---
        g.add(createSubMenuTopPanel(g, "RETURN BOOK"), BorderLayout.NORTH);

        JPanel backgroundPanel = createBackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(createSubMenuTopPanel(g, "RETURN BOOK"), BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new GridBagLayout());

        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Removed original title label as it is now in the top panel
        
        JLabel iidLabel = new JLabel("Issue ID:");
        iidLabel.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        iidLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(iidLabel, gbc);
        
        JTextField iidField = new JTextField(20);
        iidField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
        gbc.gridx = 1;
        mainPanel.add(iidField, gbc);
        
        JLabel dateLabel = new JLabel("Return Date (DD-MM-YYYY):");
        dateLabel.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        dateLabel.setForeground(Color.WHITE);
        gbc.gridy = 2;
        gbc.gridx = 0;
        mainPanel.add(dateLabel, gbc);
        
        JTextField dateField = new JTextField(20);
        dateField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
        gbc.gridx = 1;
        mainPanel.add(dateField, gbc);
        
        JButton returnButton = new JButton("RETURN BOOK");
        returnButton.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        returnButton.setBackground(new Color(0, 102, 204));
        returnButton.setForeground(Color.WHITE);
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mainPanel.add(returnButton, gbc);
        
        returnButton.addActionListener(e -> {
            String iidStr = iidField.getText();
            String returnDate = dateField.getText();
            
            if (iidStr.isEmpty() || returnDate.isEmpty()) {
                JOptionPane.showMessageDialog(g, "Please fill all fields!");
                return;
            }
            
            try {
                int iid = Integer.parseInt(iidStr);
                
                // PROPER FINE CALCULATION
                int fine = calculateFine(iid, returnDate);
                FileHandler.returnBook(iid, returnDate, fine);
                
                if (fine > 0) {
                    JOptionPane.showMessageDialog(g, "Book returned! Fine for late return: QAR " + fine);
                } else {
                    JOptionPane.showMessageDialog(g, "Book returned successfully! No fine.");
                }
                g.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(g, "Please enter valid Issue ID!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(g, "Error calculating fine: " + ex.getMessage());
            }
        });
        
        backgroundPanel.add(mainPanel, BorderLayout.CENTER);
        g.setContentPane(backgroundPanel);
        g.setVisible(true);
    }

    // ADD THIS METHOD FOR PROPER FINE CALCULATION
    private static int calculateFine(int iid, String returnDate) {
        try {
            // Get issue details from file
            List<String[]> allIssued = FileHandler.getAllIssuedBooks();
            String issueDate = "";
            int period = 0;
            
            for (String[] issue : allIssued) {
                if (Integer.parseInt(issue[0]) == iid) {
                    issueDate = issue[3]; // Issue date
                    period = Integer.parseInt(issue[6]); // Period in days
                    break;
                }
            }
            
            if (issueDate.isEmpty()) {
                throw new Exception("Issue record not found!");
            }
            
            // Parse dates
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
            java.util.Date issue = sdf.parse(issueDate);
            java.util.Date return_ = sdf.parse(returnDate);
            
            // Calculate difference in days
            long diff = return_.getTime() - issue.getTime();
            int daysBetween = (int) (diff / (1000 * 60 * 60 * 24));
            
            // Calculate fine: 10 QAR per day after due date
            int dueDays = period;
            if (daysBetween > dueDays) {
                return (daysBetween - dueDays) * 10; // 10 QAR per late day
            } else {
                return 0; // No fine if returned on time
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Return 0 fine if there's an error
        }
    }
    
    // Helper methods
    private static JPanel createBackgroundPanel() {
        return new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    ImageIcon imageIcon = new ImageIcon("library.jpg");
                    Image image = imageIcon.getImage();
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    g.setColor(new Color(70, 130, 180));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
    }
    
    // NEW: Style for the Logout/Back control buttons
    private static void styleControlButton(JButton button, Color color) {
        button.setFont(new Font("Bahnschrift", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setPreferredSize(new Dimension(100, 50)); 
    }
    
    // NEW: Helper to create the top panel with Back button and Title for sub-windows
    private static JPanel createSubMenuTopPanel(JFrame frame, String title) {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        // Back Button (Top Left)
        JButton back_but = new JButton("Back");
        styleControlButton(back_but, new Color(105, 105, 105)); // Dark Grey
        back_but.addActionListener(e -> frame.dispose());
        
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        backPanel.setOpaque(false);
        backPanel.add(back_but);
        topPanel.add(backPanel, BorderLayout.WEST);

        // Title Label
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Bahnschrift", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        return topPanel;
    }
    
    // This helper remains NON-EDITABLE for the Users and Issued Books tables.
    private static JTable createStyledTable(Object[][] data, String[] columns) {
        // Use DefaultTableModel and override isCellEditable to return false
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // make all cells non-editable
            }
        };

        JTable table = new JTable(model);

        table.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Bahnschrift", Font.BOLD, 18));
        table.setBackground(new Color(255, 255, 255, 220));
        return table;
    }
    
    private static void styleScrollPane(JScrollPane scrollPane) {
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
    }
}