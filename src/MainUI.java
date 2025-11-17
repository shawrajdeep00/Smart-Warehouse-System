import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI extends JPanel {
    private WarehouseAutomation warehouseAutomation = new WarehouseAutomation();
    private LogManager logManager;

    private static final long serialVersionUID = 1L;
    private JTextField descField;
    private JTextField rBoxIdField;
    private JTextField destinationPathField;
    private JTextField baseFolderField;
    private JTextField dateField;
    private JTextField monthField;
    private JTextField yearField;
    private JTextField fileNameField;
    private JTextField weightField;
    private JTextField sBoxIdField;
    private static JTextArea outputArea;

    /**
     * Create the panel.
     */
    public MainUI() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{-2, 90, 50, 36, 66, 0, 8, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 73, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        JLabel lblNewLabel = new JLabel("Storing");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 1;
        gbc_lblNewLabel.gridy = 0;
        add(lblNewLabel, gbc_lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Box ID:");
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 1;
        gbc_lblNewLabel_1.gridy = 1;
        add(lblNewLabel_1, gbc_lblNewLabel_1);

        sBoxIdField = new JTextField();
        sBoxIdField.setColumns(10);
        GridBagConstraints gbc_sBoxIdField = new GridBagConstraints();
        gbc_sBoxIdField.gridwidth = 3;
        gbc_sBoxIdField.insets = new Insets(0, 0, 5, 5);
        gbc_sBoxIdField.fill = GridBagConstraints.HORIZONTAL;
        gbc_sBoxIdField.gridx = 2;
        gbc_sBoxIdField.gridy = 1;
        add(sBoxIdField, gbc_sBoxIdField);

        JLabel lblNewLabel_2 = new JLabel("Weight (kg):");
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_2.gridx = 1;
        gbc_lblNewLabel_2.gridy = 2;
        add(lblNewLabel_2, gbc_lblNewLabel_2);

        weightField = new JTextField();
        weightField.setColumns(10);
        GridBagConstraints gbc_weightField = new GridBagConstraints();
        gbc_weightField.fill = GridBagConstraints.HORIZONTAL;
        gbc_weightField.gridwidth = 3;
        gbc_weightField.insets = new Insets(0, 0, 5, 5);
        gbc_weightField.gridx = 2;
        gbc_weightField.gridy = 2;
        add(weightField, gbc_weightField);

        JLabel lblNewLabel_3 = new JLabel("Description:");
        GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
        gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_3.gridx = 1;
        gbc_lblNewLabel_3.gridy = 3;
        add(lblNewLabel_3, gbc_lblNewLabel_3);

        descField = new JTextField();
        GridBagConstraints gbc_descField = new GridBagConstraints();
        gbc_descField.fill = GridBagConstraints.HORIZONTAL;
        gbc_descField.gridwidth = 3;
        gbc_descField.insets = new Insets(0, 0, 5, 5);
        gbc_descField.gridx = 2;
        gbc_descField.gridy = 3;
        add(descField, gbc_descField);
        descField.setColumns(10);

        JButton storeBoxButton = new JButton("Store Box");
        GridBagConstraints gbc_storeBoxButton = new GridBagConstraints();
        gbc_storeBoxButton.anchor = GridBagConstraints.WEST;
        gbc_storeBoxButton.insets = new Insets(0, 0, 5, 5);
        gbc_storeBoxButton.gridx = 5;
        gbc_storeBoxButton.gridy = 3;
        add(storeBoxButton, gbc_storeBoxButton);

        JLabel lblNewLabel_4 = new JLabel("Retrieving");
        GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
        gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_4.gridx = 1;
        gbc_lblNewLabel_4.gridy = 4;
        add(lblNewLabel_4, gbc_lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("Box ID:");
        GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
        gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_5.gridx = 1;
        gbc_lblNewLabel_5.gridy = 5;
        add(lblNewLabel_5, gbc_lblNewLabel_5);

        rBoxIdField = new JTextField();
        GridBagConstraints gbc_rBoxIdField = new GridBagConstraints();
        gbc_rBoxIdField.fill = GridBagConstraints.HORIZONTAL;
        gbc_rBoxIdField.gridwidth = 3;
        gbc_rBoxIdField.insets = new Insets(0, 0, 5, 5);
        gbc_rBoxIdField.gridx = 2;
        gbc_rBoxIdField.gridy = 5;
        add(rBoxIdField, gbc_rBoxIdField);
        rBoxIdField.setColumns(10);

        JButton retrieveBoxButton = new JButton("Retrieve Box");
        GridBagConstraints gbc_retrieveBoxButton = new GridBagConstraints();
        gbc_retrieveBoxButton.anchor = GridBagConstraints.WEST;
        gbc_retrieveBoxButton.insets = new Insets(0, 0, 5, 5);
        gbc_retrieveBoxButton.gridx = 5;
        gbc_retrieveBoxButton.gridy = 5;
        add(retrieveBoxButton, gbc_retrieveBoxButton);

        JLabel lblNewLabel_6 = new JLabel("Log Files");
        GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
        gbc_lblNewLabel_6.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_6.gridx = 1;
        gbc_lblNewLabel_6.gridy = 6;
        add(lblNewLabel_6, gbc_lblNewLabel_6);

        JLabel lblNewLabel_7 = new JLabel("Base Path");
        GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
        gbc_lblNewLabel_7.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_7.gridx = 1;
        gbc_lblNewLabel_7.gridy = 7;
        add(lblNewLabel_7, gbc_lblNewLabel_7);

        baseFolderField = new JTextField();
        GridBagConstraints gbc_baseFolderField = new GridBagConstraints();
        gbc_baseFolderField.fill = GridBagConstraints.HORIZONTAL;
        gbc_baseFolderField.gridwidth = 3;
        gbc_baseFolderField.insets = new Insets(0, 0, 5, 5);
        gbc_baseFolderField.gridx = 2;
        gbc_baseFolderField.gridy = 7;
        add(baseFolderField, gbc_baseFolderField);
        baseFolderField.setColumns(10);

        JLabel lblNewLabel_13 = new JLabel("e.g., AGV/Battery/Overall/System");
        GridBagConstraints gbc_lblNewLabel_13 = new GridBagConstraints();
        gbc_lblNewLabel_13.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_13.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_13.gridx = 5;
        gbc_lblNewLabel_13.gridy = 7;
        add(lblNewLabel_13, gbc_lblNewLabel_13);

        JLabel lblNewLabel_8 = new JLabel("Year Path");
        GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
        gbc_lblNewLabel_8.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_8.gridx = 1;
        gbc_lblNewLabel_8.gridy = 8;
        add(lblNewLabel_8, gbc_lblNewLabel_8);

        yearField = new JTextField();
        GridBagConstraints gbc_yearField = new GridBagConstraints();
        gbc_yearField.fill = GridBagConstraints.HORIZONTAL;
        gbc_yearField.gridwidth = 3;
        gbc_yearField.insets = new Insets(0, 0, 5, 5);
        gbc_yearField.gridx = 2;
        gbc_yearField.gridy = 8;
        add(yearField, gbc_yearField);
        yearField.setColumns(10);

        JLabel lblNewLabel_17 = new JLabel("e.g., 2025");
        GridBagConstraints gbc_lblNewLabel_17 = new GridBagConstraints();
        gbc_lblNewLabel_17.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_17.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_17.gridx = 5;
        gbc_lblNewLabel_17.gridy = 8;
        add(lblNewLabel_17, gbc_lblNewLabel_17);

        JLabel lblNewLabel_9 = new JLabel("Month Path");
        GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
        gbc_lblNewLabel_9.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_9.gridx = 1;
        gbc_lblNewLabel_9.gridy = 9;
        add(lblNewLabel_9, gbc_lblNewLabel_9);

        monthField = new JTextField();
        GridBagConstraints gbc_monthField = new GridBagConstraints();
        gbc_monthField.fill = GridBagConstraints.HORIZONTAL;
        gbc_monthField.gridwidth = 3;
        gbc_monthField.insets = new Insets(0, 0, 5, 5);
        gbc_monthField.gridx = 2;
        gbc_monthField.gridy = 9;
        add(monthField, gbc_monthField);
        monthField.setColumns(10);

        JLabel lblNewLabel_15 = new JLabel("e.g., Oct");
        GridBagConstraints gbc_lblNewLabel_15 = new GridBagConstraints();
        gbc_lblNewLabel_15.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_15.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_15.gridx = 5;
        gbc_lblNewLabel_15.gridy = 9;
        add(lblNewLabel_15, gbc_lblNewLabel_15);

        JLabel lblNewLabel_10 = new JLabel("Date Path");
        GridBagConstraints gbc_lblNewLabel_10 = new GridBagConstraints();
        gbc_lblNewLabel_10.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_10.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_10.gridx = 1;
        gbc_lblNewLabel_10.gridy = 10;
        add(lblNewLabel_10, gbc_lblNewLabel_10);

        dateField = new JTextField();
        GridBagConstraints gbc_dateField = new GridBagConstraints();
        gbc_dateField.fill = GridBagConstraints.HORIZONTAL;
        gbc_dateField.gridwidth = 3;
        gbc_dateField.insets = new Insets(0, 0, 5, 5);
        gbc_dateField.gridx = 2;
        gbc_dateField.gridy = 10;
        add(dateField, gbc_dateField);
        dateField.setColumns(10);

        JLabel lblNewLabel_18 = new JLabel("e.g., 09");
        GridBagConstraints gbc_lblNewLabel_18 = new GridBagConstraints();
        gbc_lblNewLabel_18.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_18.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_18.gridx = 5;
        gbc_lblNewLabel_18.gridy = 10;
        add(lblNewLabel_18, gbc_lblNewLabel_18);

        JLabel lblNewLabel_11 = new JLabel("File Name");
        GridBagConstraints gbc_lblNewLabel_11 = new GridBagConstraints();
        gbc_lblNewLabel_11.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_11.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_11.gridx = 1;
        gbc_lblNewLabel_11.gridy = 11;
        add(lblNewLabel_11, gbc_lblNewLabel_11);

        fileNameField = new JTextField();
        GridBagConstraints gbc_fileNameField = new GridBagConstraints();
        gbc_fileNameField.fill = GridBagConstraints.HORIZONTAL;
        gbc_fileNameField.gridwidth = 3;
        gbc_fileNameField.insets = new Insets(0, 0, 5, 5);
        gbc_fileNameField.gridx = 2;
        gbc_fileNameField.gridy = 11;
        add(fileNameField, gbc_fileNameField);
        fileNameField.setColumns(10);

        JLabel lblNewLabel_16 = new JLabel("e.g., log_12-34-56.txt");
        GridBagConstraints gbc_lblNewLabel_16 = new GridBagConstraints();
        gbc_lblNewLabel_16.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_16.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_16.gridx = 5;
        gbc_lblNewLabel_16.gridy = 11;
        add(lblNewLabel_16, gbc_lblNewLabel_16);

        JLabel lblNewLabel_12 = new JLabel("Destination Path");
        GridBagConstraints gbc_lblNewLabel_12 = new GridBagConstraints();
        gbc_lblNewLabel_12.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_12.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_12.gridx = 1;
        gbc_lblNewLabel_12.gridy = 12;
        add(lblNewLabel_12, gbc_lblNewLabel_12);

        destinationPathField = new JTextField();
        GridBagConstraints gbc_destinationPathField = new GridBagConstraints();
        gbc_destinationPathField.fill = GridBagConstraints.HORIZONTAL;
        gbc_destinationPathField.gridwidth = 3;
        gbc_destinationPathField.insets = new Insets(0, 0, 5, 5);
        gbc_destinationPathField.gridx = 2;
        gbc_destinationPathField.gridy = 12;
        add(destinationPathField, gbc_destinationPathField);
        destinationPathField.setColumns(10);

        JLabel lblNewLabel_14 = new JLabel("e.g., Logs/Archive/2025/Oct/28");
        GridBagConstraints gbc_lblNewLabel_14 = new GridBagConstraints();
        gbc_lblNewLabel_14.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_14.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_14.gridx = 5;
        gbc_lblNewLabel_14.gridy = 12;
        add(lblNewLabel_14, gbc_lblNewLabel_14);

        JButton viewLogButton = new JButton("View Log");
        GridBagConstraints gbc_viewLogButton = new GridBagConstraints();
        gbc_viewLogButton.fill = GridBagConstraints.HORIZONTAL;
        gbc_viewLogButton.insets = new Insets(0, 0, 5, 5);
        gbc_viewLogButton.gridx = 2;
        gbc_viewLogButton.gridy = 13;
        add(viewLogButton, gbc_viewLogButton);

        JButton deleteLogButton = new JButton("Delete Log");
        GridBagConstraints gbc_deleteLogButton = new GridBagConstraints();
        gbc_deleteLogButton.insets = new Insets(0, 0, 5, 5);
        gbc_deleteLogButton.gridx = 3;
        gbc_deleteLogButton.gridy = 13;
        add(deleteLogButton, gbc_deleteLogButton);

        JButton moveLogButton = new JButton("Move Log");
        GridBagConstraints gbc_moveLogButton = new GridBagConstraints();
        gbc_moveLogButton.insets = new Insets(0, 0, 5, 5);
        gbc_moveLogButton.gridx = 4;
        gbc_moveLogButton.gridy = 13;
        add(moveLogButton, gbc_moveLogButton);

        JButton displayAllInformationButton = new JButton("Display All Information");
        GridBagConstraints gbc_displayAllInformationButton = new GridBagConstraints();
        gbc_displayAllInformationButton.fill = GridBagConstraints.HORIZONTAL;
        gbc_displayAllInformationButton.gridwidth = 3;
        gbc_displayAllInformationButton.insets = new Insets(0, 0, 5, 5);
        gbc_displayAllInformationButton.gridx = 2;
        gbc_displayAllInformationButton.gridy = 14;
        add(displayAllInformationButton, gbc_displayAllInformationButton);

        JButton clearButton = new JButton("Clear");
        GridBagConstraints gbc_clearButton = new GridBagConstraints();
        gbc_clearButton.anchor = GridBagConstraints.EAST;
        gbc_clearButton.insets = new Insets(0, 0, 5, 5);
        gbc_clearButton.gridx = 5;
        gbc_clearButton.gridy = 14;
        add(clearButton, gbc_clearButton);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
        gbc_scrollPane.gridwidth = 6;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 15;
        add(scrollPane, gbc_scrollPane);

        outputArea = new JTextArea();
        scrollPane.setViewportView(outputArea);

        storeBoxButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = sBoxIdField.getText().trim();
                String weight = weightField.getText().trim();
                String desc = descField.getText().trim();

                new Thread(() -> {
                    warehouseAutomation.storeBox(id, weight, desc);
                }).start();
                clearAllFields();
            }
        });

        retrieveBoxButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = rBoxIdField.getText().trim(); // get input from GUI field

                new Thread(() -> {
                    warehouseAutomation.retrieveBox(id);
                }).start();
                clearAllFields();
            }
        });

        viewLogButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String baseFolder = baseFolderField.getText().trim();
                String year = yearField.getText().trim();
                String month = monthField.getText().trim();
                String date = dateField.getText().trim();
                String fileName = fileNameField.getText().trim();

                logManager = new LogManager(baseFolder);
                try {
                    logManager.viewLog(baseFolder, year, month, date, fileName);
                } catch (InvalidPathException ex) {
                    throw new RuntimeException(ex);
                }
                clearAllFields();
            }
        });

        deleteLogButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String baseFolder = baseFolderField.getText().trim();
                String year = yearField.getText().trim();
                String month = monthField.getText().trim();
                String date = dateField.getText().trim();
                String fileName = fileNameField.getText().trim();

                logManager = new LogManager(baseFolder);
                try {
                    logManager.deleteLog(baseFolder, year, month, date, fileName);
                } catch (InvalidPathException ex) {
                    throw new RuntimeException(ex);
                }
                clearAllFields();
            }
        });

        moveLogButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String baseFolder = baseFolderField.getText().trim();
                String year = yearField.getText().trim();
                String month = monthField.getText().trim();
                String date = dateField.getText().trim();
                String fileName = fileNameField.getText().trim();
                String destinationPath = destinationPathField.getText().trim();

                logManager = new LogManager(baseFolder);
                try {
                    logManager.moveLog(baseFolder, year, month, date, fileName, destinationPath);
                } catch (InvalidPathException ex) {
                    throw new RuntimeException(ex);
                }
                clearAllFields();
            }
        });

        displayAllInformationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearAllFields();
                warehouseAutomation.displayInfo();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                outputArea.setText("");
                clearAllFields();
            }
        });

    }

    public static void appendOutput(String text) {
        outputArea.append(text + "\n");
    }

    public void clearAllFields() {
        sBoxIdField.setText("");
        weightField.setText("");
        descField.setText("");
        rBoxIdField.setText("");
        baseFolderField.setText("");
        yearField.setText("");
        monthField.setText("");
        dateField.setText("");
        fileNameField.setText("");
        destinationPathField.setText("");
    }
}