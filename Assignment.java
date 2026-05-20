import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// INTERFACE
// Contributed by Siti Nur Farah Maisarah (106387)
// Defines the contract for any encoder class.
// Separates WHAT the encoder does from HOW it does it.
interface Encodable {
    boolean checkStringValidity(String inputText);
    int countCharacters(String inputText);
    int generateShift();
    String applyCipher(String inputText, int shift);
    void encode();
    String getResultText();
}

// Private class (not public)
// This class handles the encoding logic for the String Encoder program.
// It validates input, counts characters, generates a group-specific shift,
// and applies a Caesar-style cipher to encode the input string.
// Contributed by Nur Syukrinah (97717) - implements Encodable interface
class Encoded implements Encodable {

    private String inputText;
    private int charCount;
    private String resultText;
    private final String groupID = "G02/CS-G07"; // Member 3 will finalize

    // Default constructor
    // Contributed by Siti Nur Farah Maisarah (106387)
    public Encoded() {}

    // Constructor with input
    // Sets the inputText field for later use in encode().
    // Contributed by Siti Nur Farah Maisarah (106387)
    public Encoded(String inputText) {
        this.inputText = inputText;
    }

    //getter for GroupID in main
    // Contributed by Bong Ming Meng (103541)
    public String getGroupID() {
        return groupID;
    }

    // VALIDATION
    // Contributed by Siti Nur Farah Maisarah (106387)
    // Checks whether the input string contains only
    // Lowercase letters (a–z), Digits (0–9) and Spaces
    // Returns true if all characters are valid, false otherwise.
    @Override
    public boolean checkStringValidity(String inputText) {
        // Reject null or empty strings immediately
        if (inputText == null || inputText.isEmpty()) {
            return false;
        }

        for (int i = 0; i < inputText.length(); i++) {
            char ch = inputText.charAt(i);

            // Allow lowercase letters, digits, and spaces only.
            // Uppercase letters, punctuation, etc. are not permitted.
            if (!((ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || ch == ' ')) {
                return false;
            }
        }
        return true;
    }

    // COUNT CHARACTERS
    // Contributed by Nur Syukrinah (97717)
    // Counts only the alphanumeric (non-space) characters in the input.
    @Override
    public int countCharacters(String inputText) {
        int count = 0;

        for (int i = 0; i < inputText.length(); i++) {
            char ch = inputText.charAt(i);

            // Count only letters and digit
            if (Character.isLetterOrDigit(ch)) {
                count++;
            }
        }
        return count;
    }

    // GENERATE SHIFT
    // Contributed by Nashrur Aisyha Hani (102776)
    // Sums all digits found in groupID to produce a fixed group shift.
    @Override
    public int generateShift() {
          int sum = 0;

        for (int i = 0; i < groupID.length(); i++ ){
            char ch = groupID.charAt(i);

            if (Character.isDigit(ch)){
                sum += Character.getNumericValue(ch);
            }
        }
        return sum;
    }

    // APPLY CIPHER
    // Contributed by Bong Ming Meng (103541)
    // Applies a Caesar-style shift cipher to the input string:
    //   - Lowercase letters are shifted within a–z (mod 26)
    //   - Digits are shifted within 0–9 (mod 10)
    //   - Spaces are preserved unchanged
    // Formula for letters : (c - 'a' + shift) % 26 + 'a'
    // Formula for digits  : (c - '0' + shift) % 10 + '0'
    @Override
    public String applyCipher(String inputText, int shift) {
        if (inputText == null || inputText.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < inputText.length(); i++) {
            char c = inputText.charAt(i);

            if (c >= 'a' && c <= 'z') {
                // Shift lowercase letter, wrapping around at 'z'
                char encoded = (char) ((c - 'a' + shift) % 26 + 'a');
                result.append(encoded);
            } 
            
            else if (c >= '0' && c <= '9') {
                // Shift digit, wrapping around at '9'
                char encoded = (char) ((c - '0' + shift) % 10 + '0');
                result.append(encoded);
            } 
            
            else if (c == ' ') {
                // Spaces are not encoded - passed through as-is
                result.append(' ');
            }
        }
        return result.toString();
    }

    // Returns the final shift value (groupShift + charCount) for display purposes
    public int getFinalShift() {
        return generateShift() + countCharacters(inputText);
    }

    // Returns the encoded result text after encode() has been called
    @Override
    public String getResultText() {
        return resultText;
    }

    // MAIN ENCODE METHOD
    @Override
    public void encode() { 
        // Validate input
        if (!checkStringValidity(inputText)) {
            resultText = "INVALID INPUT";
            return;
        }
        //Count non-space characters
        int charCount = countCharacters(inputText); 
        //Generate a fixed shift value from the hardcoded groupID (G02/CS-G07)
        int groupShift = generateShift(); 
        //Compute final shift
        int finalShift = groupShift + charCount; 
        //Apply cipher and store result
        resultText = applyCipher(inputText, finalShift); 
    }
}

// PUBLIC MAIN CLASS
// Contributed by Members Siti Nur Farah Maisarah (106387), Nur Syukrinah (97717), Nashrur Aisyha Hani (102776) and Bong Ming Meng (103541)
// Builds and displays the Java Swing GUI for the String Encoder program.
public class Assignment {

    public static void main(String[] args) {

        JFrame frame = new JFrame("String Encoder");
        frame.setSize(420, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // INPUT PANEL
        // Contributed by Siti Nur Farah Maisarah (106387)
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        JLabel inputLabel = new JLabel("Enter text to encode:");
        JTextField inputField = new JTextField();
        inputPanel.add(inputLabel, BorderLayout.NORTH);
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        frame.add(inputPanel, BorderLayout.NORTH);

        // OUTPUT DISPLAY AREA
        // Contributed by Nur Syukrinah (97717) and Nashrur Aisyah Hani (102776)
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        displayArea.setMargin(new Insets(5, 5, 5, 5));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        frame.add(scrollPane, BorderLayout.CENTER);

        // ENCODE BUTTON & LOGIC
        // Contributed by Member Bong Ming Meng (103541)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton encodeButton = new JButton("Encode");
        buttonPanel.add(encodeButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for the Encode button.
        encodeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String input = inputField.getText();
                
                 // Use Encodable interface type - polymorphism
                Encodable obj = new Encoded(input);

                // Validate input - reject if not lowercase alphanumeric + spaces
                if (!obj.checkStringValidity(input)) {
                    displayArea.setText("ERROR: Invalid input.\n"
                            + "Please enter only lowercase letters (a–z), digits (0–9), and spaces.");
                    return;
                }

                // Count non-space characters
                int charCount = obj.countCharacters(input);

                // Generate group-specific shift from hardcoded groupID
                int groupShift = obj.generateShift();

                // Compute final shift = groupShift + number of non-space characters
                int finalShift = groupShift + charCount;

                // Apply cipher to produce encoded string
                String encodedResult = obj.applyCipher(input, finalShift);

                // Display results
                displayArea.setText(
                     "Non-space characters : " + charCount + "\n" +
                    "Final shift used      : " + finalShift + "\n" +
                    "Encoded result        : " + encodedResult + "\n\n" +
                    "Encoding completed successfully."
                );
            }
        });

        frame.setVisible(true);
    }
}
