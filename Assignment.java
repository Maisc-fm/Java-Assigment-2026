import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Private class (not public)
// This class handles the encoding logic for the String Encoder program.
// It validates input, counts characters, generates a group-specific shift,
// and applies a Caesar-style cipher to encode the input string.
class Encoded {

    private String inputText;
    private int charCount;
    private String resultText;
    // Secret group ID hardcoded — not visible or changeable at runtime.
    // The groupShift is derived from this value using hashCode().
    // Replace "GXX/XX-GXX" with your actual group ID before submission.
    private final String groupID = "G02/CS-G07"; // Member 3 will finalize

    // Default constructor
    public Encoded() {}

    // Constructor with input
    // Sets the inputText field for later use in encode().
    public Encoded(String inputText) {
        this.inputText = inputText;
    }

    //getter for GroupID in main
    public String getGroupID() {
        return groupID;
    }

    // VALIDATION
    // Contributed by Siti Nur Farah Maisarah (106387)
    // Checks whether the input string contains only:
    //   - Lowercase letters (a–z)
    //   - Digits (0–9)
    //   - Spaces
    // Returns true if all characters are valid, false otherwise.
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
    // Spaces are excluded because the encoding shift is based on
    // the number of meaningful characters only.
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
    // Derives a consistent group-specific shift value (1–10) from the
    // hardcoded groupID using Java's built-in hashCode() method.
    // This value is fixed for a given groupID, so the same group always
    // produces the same groupShift — making encoded output traceable.
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
    //
    // Formula for letters : (c - 'a' + shift) % 26 + 'a'
    // Formula for digits  : (c - '0' + shift) % 10 + '0'
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
                // Spaces are not encoded — passed through as-is
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
    public String getResultText() {
        return resultText;
    }

    // ==============================
    // MAIN ENCODE METHOD
    // ==============================
    // Orchestrates the full encoding pipeline:
    //   1. Validate input
    //   2. Count non-space characters
    //   3. Generate group-specific shift
    //   4. Compute final shift
    //   5. Apply cipher and store result
    public void encode() {
        if (!checkStringValidity(inputText)) {
            resultText = "INVALID INPUT";
            return;
        }
        int charCount = countCharacters(inputText);
        int groupShift = generateShift();
        int finalShift = groupShift + charCount;
        resultText = applyCipher(inputText, finalShift);
    }
}

// PUBLIC MAIN CLASS
// Contributed by Members 1 & 4
// Builds and displays the Java Swing GUI for the String Encoder program.
public class Assignment {

    public static void main(String[] args) {

        JFrame frame = new JFrame("String Encoder");
        frame.setSize(420, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // ==============================
        // MEMBER 1 - INPUT PANEL
        // ==============================
        // Contributed by Member 1
        // A top panel containing a label and text field for user input.
        // Only lowercase letters, digits, and spaces are accepted.
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        JLabel inputLabel = new JLabel("Enter text to encode:");
        JTextField inputField = new JTextField();
        inputPanel.add(inputLabel, BorderLayout.NORTH);
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        frame.add(inputPanel, BorderLayout.NORTH);

        // ==============================
        // MEMBER 2 - OUTPUT DISPLAY AREA
        // ==============================
        // Contributed by Member 2
        // A scrollable, non-editable text area that shows:
        //   - Number of non-space characters
        //   - Final shift value used for encoding
        //   - The encoded result string
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        displayArea.setMargin(new Insets(5, 5, 5, 5));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        frame.add(scrollPane, BorderLayout.CENTER);

        // ==============================
        // MEMBER 4 - ENCODE BUTTON & LOGIC
        // ==============================
        // Contributed by Member 4
        // The Encode button triggers the full encoding pipeline when clicked.
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton encodeButton = new JButton("Encode");
        buttonPanel.add(encodeButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for the Encode button.
        // Steps:
        //   1. Read input from the text field
        //   2. Validate — show error and stop if invalid
        //   3. Count non-space characters
        //   4. Compute final shift (groupShift + charCount)
        //   5. Apply cipher and display results
        //
        // NOTE: groupShift is intentionally NOT displayed per assignment spec.
        encodeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String input = inputField.getText();
                Encoded obj = new Encoded(input);

                // Step 1: Validate input — reject if not lowercase alphanumeric + spaces
                if (!obj.checkStringValidity(input)) {
                    displayArea.setText("ERROR: Invalid input.\n"
                            + "Please enter only lowercase letters (a–z), digits (0–9), and spaces.");
                    return;
                }

                // Step 2: Count non-space characters
                int charCount = obj.countCharacters(input);

                // Step 3: Generate group-specific shift from hardcoded groupID
                int groupShift = obj.generateShift();

                // Step 4: Compute final shift = groupShift + number of non-space characters
                int finalShift = groupShift + charCount;

                // Step 5: Apply cipher to produce encoded string
                String encodedResult = obj.applyCipher(input, finalShift);

                // Display results — groupShift is intentionally hidden per assignment spec
                displayArea.setText(
                    "Non-space characters : " + charCount + "\n" +
                    "Group Shift          : " + groupShift + " (from hash of " + obj.getGroupID() + " )" + "\n" +
                    "Final Shift          : " + groupShift + " + " + charCount + " = " + finalShift + "\n\n" +
                    "Output: \n" +
                    encodedResult + "\n\n" +
                    "Encoding completed successfully."
                );
            }
        });

        frame.setVisible(true);
    }
}