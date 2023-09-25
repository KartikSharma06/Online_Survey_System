import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class SurveyQuestion {
    private String question;
    private List<String> options;

    public SurveyQuestion(String question, List<String> options) {
        this.question = question;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }
}

class SurveyForm extends JFrame {
    private List<SurveyQuestion> questions;
    private List<String> answers;

    private int currentQuestionIndex;

    private JLabel questionLabel;
    private ButtonGroup optionGroup;
    private JButton nextButton;

    public SurveyForm(List<SurveyQuestion> questions) {
        this.questions = questions;
        this.answers = new ArrayList<>();
        this.currentQuestionIndex = 0;

        setTitle("Online Survey");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createUI();
    }

    private void createUI() {
        questionLabel = new JLabel(questions.get(currentQuestionIndex).getQuestion());
        add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(0, 1));
        optionGroup = new ButtonGroup();

        for (String option : questions.get(currentQuestionIndex).getOptions()) {
            JRadioButton radioButton = new JRadioButton(option);
            radioButton.setActionCommand(option);
            optionGroup.add(radioButton);
            optionsPanel.add(radioButton);
        }

        add(optionsPanel, BorderLayout.CENTER);

        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = optionGroup.getSelection().getActionCommand();
                answers.add(selectedOption);

                currentQuestionIndex++;

                if (currentQuestionIndex < questions.size()) {
                    questionLabel.setText(questions.get(currentQuestionIndex).getQuestion());
                    optionGroup.clearSelection();

                    // Update options for the new question
                    optionsPanel.removeAll();
                    for (String option : questions.get(currentQuestionIndex).getOptions()) {
                        JRadioButton radioButton = new JRadioButton(option);
                        radioButton.setActionCommand(option);
                        optionGroup.add(radioButton);
                        optionsPanel.add(radioButton);
                    }

                    optionsPanel.revalidate();
                    optionsPanel.repaint();
                } else {
                    showSurveyResults();
                }
            }
        });

        add(nextButton, BorderLayout.SOUTH);
    }

    private void showSurveyResults() {
        StringBuilder resultMessage = new StringBuilder("Survey Results:\n");
        for (int i = 0; i < questions.size(); i++) {
            resultMessage.append("Question: ").append(questions.get(i).getQuestion()).append("\n");
            resultMessage.append("Answer: ").append(answers.get(i)).append("\n");
        }

        JOptionPane.showMessageDialog(this, resultMessage.toString(), "Survey Results", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
}

public class Main{
    public static void main(String[] args) {
        List<SurveyQuestion> surveyQuestions = new ArrayList<>();
        surveyQuestions.add(new SurveyQuestion("Which is your favourite cusine?", List.of("Junk", "Indian", "Italian")));
        surveyQuestions.add(new SurveyQuestion("Which is your favorite drink?", List.of("Cold drinks", "Alcoholic drink", "Juices")));
        surveyQuestions.add(new SurveyQuestion("How often do you cook at Home",List.of("Daily","Monthly","Rarely")));
        surveyQuestions.add(new SurveyQuestion("Which one do you prefer",List.of("Spicy","Street","Both")));
        surveyQuestions.add(new SurveyQuestion("Where do you prefer to eat",List.of("Street vendors","restaurant","Home")));

        SwingUtilities.invokeLater(() -> {
            SurveyForm surveyForm = new SurveyForm(surveyQuestions);
            surveyForm.setVisible(true);
        });
    }
}