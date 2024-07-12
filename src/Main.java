import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Font;

public class Main {

    public static void main(String[] args) {
        JFrame window = createWindow();
        JTextArea textArea = createTextArea(window);
        createScrollPane(textArea, window);

        JMenuBar menuBar = createMenuBar(window);
        JMenu menuFile = createMenu(menuBar, "File");
        JMenu menuEdit = createMenu(menuBar, "Edit");
        JMenu menuFormat = createMenu(menuBar, "Format");

        fileButtonFunction(textArea, menuFile, window);
        editButtonFunction(textArea, menuEdit);
        formatButtonFunction(textArea, menuFormat);

        window.setVisible(true);
    }

    private static void fileButtonFunction(JTextArea textArea, JMenu menuFile, JFrame window) {
        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });
        menuFile.add(newItem);

        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile(textArea, window);
            }
        });
        menuFile.add(openItem);

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        menuFile.add(saveItem);

        JMenuItem saveAsItem = new JMenuItem("Save as");
        saveAsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFileAs(window);
            }
        });
        menuFile.add(saveAsItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuFile.add(exitItem);
    }

    private static void openFile(JTextArea textArea, JFrame window) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(window);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                textArea.setText("");
                String line;
                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(window, "File read error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void saveFileAs(JFrame window) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(window);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String selectedFile = fileChooser.getSelectedFile().getAbsolutePath();
        }
    }

    private static void editButtonFunction(JTextArea textArea, JMenu menuEdit) {
        JMenuItem cutItem = new JMenuItem("Cut");
        cutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.cut();
            }
        });
        menuEdit.add(cutItem);

        JMenuItem copyItem = new JMenuItem("Copy");
        copyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.copy();
            }
        });
        menuEdit.add(copyItem);

        JMenuItem pasteItem = new JMenuItem("Paste");
        pasteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.paste();
            }
        });
        menuEdit.add(pasteItem);

        JMenuItem selectAllItem = new JMenuItem("Select All");
        selectAllItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.selectAll();
            }
        });
        menuEdit.add(selectAllItem);

        JMenuItem undoItem = new JMenuItem("Undo");
        menuEdit.add(undoItem);

        JMenuItem redoItem = new JMenuItem("Redo");
        menuEdit.add(redoItem);
    }

    private static void formatButtonFunction(JTextArea textArea, JMenu menuFormat) {
        JMenuItem wordWrapItem = new JMenuItem("Word Wrap");
        wordWrapItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setLineWrap(!textArea.getLineWrap());
            }
        });
        menuFormat.add(wordWrapItem);

        JMenuItem fontItem = new JMenuItem("Font");
        fontItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fontName = JOptionPane.showInputDialog("Enter font name:");
                int fontSize = Integer.parseInt(JOptionPane.showInputDialog("Enter font size:"));
                textArea.setFont(new Font(fontName, Font.PLAIN, fontSize));
            }
        });
        menuFormat.add(fontItem);
    }

    private static JMenu createMenu(JMenuBar menuBar, String title) {
        JMenu menu = new JMenu(title);
        menuBar.add(menu);
        return menu;
    }

    private static JMenuBar createMenuBar(JFrame window) {
        JMenuBar menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);
        return menuBar;
    }

    private static void createScrollPane(JTextArea textArea, JFrame window) {
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        window.add(scrollPane);
    }

    private static JTextArea createTextArea(JFrame window) {
        JTextArea textArea = new JTextArea();
        window.add(textArea);
        return textArea;
    }

    private static JFrame createWindow() {
        JFrame window = new JFrame("Notepad");
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        return window;
    }
}
