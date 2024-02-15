
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * Clasa QuizApplication implementeaza o aplicatie tip quiz
 * cu interfata grafica bazata pe Swing.
 */

public class QuizApplication {
	
	private static int currentQuestionIndex = 0;
    private static List<Question> questions;
    private static JLabel questionLabel;
    private static JButton[] optionButtons;
    private static JFrame frame;
    
    /**
     * Punctul de intrare principal al aplicatiei.
     * @param args argumentele liniei de comanda (nu sunt utilizate)
     */
    public static void main(String[] args) {
    	
    	try {
    	    for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
    	        if ("Nimbus".equals(info.getName())) { 
    	            UIManager.setLookAndFeel(info.getClassName());
    	            break;
    	        }
    	    }
    	} catch (Exception e) {
    	}

    	questionLabel = new JLabel("Question", SwingConstants.CENTER);
    	
    	 initializeDatabase();
    	    DatabaseUtils.populateDatabase(); 
    	    
    	    frame = new JFrame("Formula 1 Quiz"); 
    	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    frame.setSize(1000, 600);
    	    
    	    frame.setVisible(true);

    
        JButton btnLeaderboard = new JButton("Show Leaderboard");
        JButton btnNewGame = new JButton("Start a New Game");
        JButton btnContinueGame = new JButton("Continue an Old Game");
        JButton btnExit = new JButton("Exit the App");
        


        customizeButton(btnLeaderboard);
        customizeButton(btnNewGame);
        customizeButton(btnContinueGame);
        customizeButton(btnExit);

        btnLeaderboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel leaderboardPanel = createLeaderboardPanel(); 
                updateFrameContent(leaderboardPanel); 
                
            }
        });

        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questions = DatabaseUtils.getRandomQuestions(10); 
                if (!questions.isEmpty()) {
                    currentQuestionIndex = 0; 
                    JPanel quizPanel = createQuizPanel(); 
                    displayQuestion(questions.get(currentQuestionIndex)); 
                    updateFrameContent(quizPanel); 
                } else {
                    JOptionPane.showMessageDialog(frame, "No questions available.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        btnContinueGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameState savedState = DatabaseUtils.loadGameState();
                if (savedState != null && savedState.getQuestions() != null && !savedState.getQuestions().isEmpty()) {
                    questions = savedState.getQuestions();
                    currentQuestionIndex = savedState.getCurrentQuestionIndex();
                    userScore = savedState.getUserScore();

                    JPanel quizPanel = createQuizPanel();
                    displayQuestion(questions.get(currentQuestionIndex));
                    updateFrameContent(quizPanel);
                } else {
                    JOptionPane.showMessageDialog(frame, "No saved game state available.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (questions != null && !questions.isEmpty() && currentQuestionIndex < questions.size()) {
              
                    DatabaseUtils.saveGameState(currentQuestionIndex, userScore, questions);
                }
                frame.dispose(); 
            }
        });



        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    
        JPanel pnlLeaderboard = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel pnlNewGame = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel pnlContinueGame = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel pnlExit = new JPanel(new FlowLayout(FlowLayout.CENTER));


        pnlLeaderboard.add(btnLeaderboard);
        pnlNewGame.add(btnNewGame);
        pnlContinueGame.add(btnContinueGame);
        pnlExit.add(btnExit);

  
        mainPanel.add(pnlLeaderboard);
        mainPanel.add(pnlNewGame);
        mainPanel.add(pnlContinueGame);
        mainPanel.add(pnlExit);

      
        frame.getContentPane().add(mainPanel);

      
        frame.setVisible(true);
    }
    
    /**
     * Actualizeaza continutul ferestrei principale cu un nou panou.
     * @param newPanel panoul nou care va inlocui continutul curent
     */
    
    public static void updateFrameContent(JPanel newPanel) {
        frame.getContentPane().removeAll(); 
        frame.getContentPane().add(newPanel); 
        frame.revalidate();
        frame.repaint();
    }
    
    /**
     * Afiseaza meniul principal al aplicatiei.
     */
    
    public static void showMainMenu() {
        // Reset the quiz state
        currentQuestionIndex = 0;
        userScore = 0;
        questions = new ArrayList<>();

        frame.getContentPane().removeAll();
        frame.getContentPane().add(createMainMenuPanel());
        frame.revalidate();
        frame.repaint();
    }
    
    /**
     * Schimba ecranul curent cu un nou panou.
     * @param newPanel panoul care va fi afisat
     */
    
    public static void switchToScreen(JPanel newPanel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(newPanel);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Creeaza si returneaza panoul principal al meniului.
     * @return panoul meniului principal cu butoanele corespunzatoare
     */
    
    public static JPanel createMainMenuPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JButton btnLeaderboard = new JButton("Show Leaderboard");
        JButton btnNewGame = new JButton("Start a New Game");
        JButton btnContinueGame = new JButton("Continue an Old Game");
        JButton btnExit = new JButton("Exit the App");

      
        customizeButton(btnLeaderboard);
        customizeButton(btnNewGame);
        customizeButton(btnContinueGame);
        customizeButton(btnExit);


        btnLeaderboard.addActionListener(e -> {
            JPanel leaderboardPanel = createLeaderboardPanel();
            updateFrameContent(leaderboardPanel);
        });

        btnNewGame.addActionListener(e -> {
            questions = DatabaseUtils.getRandomQuestions(10); 
            currentQuestionIndex = 0;
            userScore = 0;
            displayQuestion(questions.get(currentQuestionIndex)); 
            JPanel quizPanel = createQuizPanel(); 
            updateFrameContent(quizPanel); 
        });

        btnContinueGame.addActionListener(e -> {
     
            GameState savedState = DatabaseUtils.loadGameState();
            if (savedState != null && savedState.getQuestions() != null && !savedState.getQuestions().isEmpty()) {
                questions = savedState.getQuestions();
                currentQuestionIndex = savedState.getCurrentQuestionIndex();
                userScore = savedState.getUserScore();

                JPanel quizPanel = createQuizPanel();
                displayQuestion(questions.get(currentQuestionIndex));
                updateFrameContent(quizPanel);
            } else {
                JOptionPane.showMessageDialog(frame, "No saved game state available.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnExit.addActionListener(e -> frame.dispose());

        mainPanel.add(createButtonPanel(btnLeaderboard));
        mainPanel.add(createButtonPanel(btnNewGame));
        mainPanel.add(createButtonPanel(btnContinueGame));
        mainPanel.add(createButtonPanel(btnExit));

        return mainPanel;
    }
    
    /**
     * Creeaza si returneaza un panou care contine un singur buton.
     * @param button butonul care va fi inclus in panou
     * @return panoul care contine butonul dat
     */

    public static JPanel createButtonPanel(JButton button) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(button);
        return buttonPanel;
    }

    /**
     * Personalizeaza aspectul unui buton.
     * @param button butonul care urmeaza sa fie personalizat
     */
    
    public static void customizeButton(JButton button) {
      
        Dimension buttonSize = new Dimension(250, 60); 
        button.setPreferredSize(buttonSize);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(33, 150, 243)); 
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(25, 135, 220)); 
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(33, 150, 243)); 
            }
        });
    }
    
    /**
     * Creeaza si returneaza panoul pentru quiz.
     * @return panoul quizului care include intrebari si optiuni de raspuns
     */
    
    public static JPanel createQuizPanel() {
        JPanel quizPanel = new JPanel(new BorderLayout());

   
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

  
        questionLabel = new JLabel("Question", SwingConstants.CENTER);
        centerPanel.add(questionLabel, gbc);


        JPanel optionsPanel = new JPanel(new GridBagLayout());
        optionButtons = new JButton[4];
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new JButton("Option " + (i + 1));
            int finalI = i;
            optionButtons[i].addActionListener(e -> handleOptionSelection(finalI));
            customizeButton(optionButtons[i]);
            optionsPanel.add(optionButtons[i], gbc);
        }

   
        centerPanel.add(Box.createVerticalGlue(), gbc);
        centerPanel.add(optionsPanel, gbc);

       
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> displayNextQuestion());
        controlPanel.add(nextButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
        
            DatabaseUtils.saveGameState(currentQuestionIndex, userScore, questions);
            
            frame.dispose(); 
        });



       
        centerPanel.add(controlPanel, gbc);

       
        quizPanel.add(centerPanel, BorderLayout.CENTER);

        return quizPanel;
    }

    /**
     * Initializeaza baza de date pentru aplicatie.
     */
    
    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:quiz.db")) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
         
                try (Statement stmt = conn.createStatement()) {
                    String sql = "CREATE TABLE IF NOT EXISTS questions (" +
                                 " id INTEGER PRIMARY KEY," +
                                 " question TEXT NOT NULL," +
                                 " option1 TEXT NOT NULL," +
                                 " option2 TEXT NOT NULL," +
                                 " option3 TEXT NOT NULL," +
                                 " option4 TEXT NOT NULL," +
                                 " answer INTEGER NOT NULL)";
                    stmt.execute(sql);
                }

        
                try (Statement stmt = conn.createStatement()) {
                    String leaderboardSql = "CREATE TABLE IF NOT EXISTS leaderboard (" +
                                            " name TEXT NOT NULL," +
                                            " score INTEGER NOT NULL)";
                    stmt.execute(leaderboardSql);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returneaza o lista de intrebari aleatorii din baza de date.
     * @param numberOfQuestions numarul de intrebari solicitate
     * @return o lista de obiecte Question
     */
    public List<Question> getRandomQuestions(int numberOfQuestions) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions ORDER BY RANDOM() LIMIT ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:quiz.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, numberOfQuestions); 

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                questions.add(new Question(rs.getString("question"), 
                                           new String[]{rs.getString("option1"), 
                                                        rs.getString("option2"), 
                                                        rs.getString("option3"), 
                                                        rs.getString("option4")},
                                           rs.getInt("answer")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return questions;
    }
    
    /**
     * Creeaza interfata grafica a utilizatorului si o afiseaza.
     */
    
    public static void createAndShowGUI() {
        frame = new JFrame("Formula 1 Quiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        questionLabel = new JLabel();
        questionLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            int finalI = i;
            optionButtons[i].addActionListener(e -> handleOptionSelection(finalI));
            optionsPanel.add(optionButtons[i]);
        }

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> displayNextQuestion());

        frame.add(questionLabel, BorderLayout.NORTH);
        frame.add(optionsPanel, BorderLayout.CENTER);
        frame.add(nextButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
    
    /**
     * Afiseaza o intrebare specifica pe ecran.
     * @param question intrebarea care va fi afisata
     */
    
    public static void displayQuestion(Question question) {
        if (questionLabel == null) {
            questionLabel = new JLabel("Question", SwingConstants.CENTER);
        }
        questionLabel.setText(question.getQuestionText());
        String[] options = question.getOptions();
        for (int i = 0; i < options.length; i++) {
            optionButtons[i].setText(options[i]);
        }
    }


    public static int userScore = 0;
    
    /**
     * Trateaza selectia unei optiuni de raspuns si actualizeaza scorul.
     * @param optionIndex indexul optiunii selectate
     */
    
    public static void handleOptionSelection(int optionIndex) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        if (optionIndex == currentQuestion.getCorrectAnswerIndex()) {
            userScore++; 
        }

     
        Timer timer = new Timer(500, e -> displayNextQuestion()); 
        timer.setRepeats(false); 
        timer.start();
    }

    /**
     * Creeaza si returneaza panoul pentru clasament.
     * @return panoul care afiseaza clasamentul
     */
    
    public static JPanel createLeaderboardPanel() {
        JPanel leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new BoxLayout(leaderboardPanel, BoxLayout.Y_AXIS));

        List<Pair<String, Integer>> scores = DatabaseUtils.getTopScores();
        for (Pair<String, Integer> score : scores) {
            
            JPanel scorePanel = new JPanel(new GridBagLayout());
            JLabel scoreLabel = new JLabel(score.getKey() + " - " + score.getValue(), SwingConstants.CENTER);
            scorePanel.add(scoreLabel); 

            leaderboardPanel.add(scorePanel); 
        }

     
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
        backButton.addActionListener(e -> showMainMenu());
        customizeButton(backButton); 

        leaderboardPanel.add(backButton); 

        return leaderboardPanel;
    }


    /**
     * Afiseaza urmatoarea intrebare din quiz sau termina quizul daca s-au epuizat intrebarile.
     */
    
    public static void displayNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            displayQuestion(questions.get(currentQuestionIndex));
        } else {
            endQuiz();
        }
    }


    /**
     * Finalizeaza quizul si afiseaza scorul utilizatorului.
     */
    
    public static void endQuiz() {
    	
    	String userName = JOptionPane.showInputDialog(frame, 
    	        "Enter your name:", 
    	        "Name Entry", 
    	        JOptionPane.PLAIN_MESSAGE);

    	    if (userName != null && !userName.trim().isEmpty()) {
    	       
    	        DatabaseUtils.saveScore(userName, userScore);
    	    }
      
        JOptionPane.showMessageDialog(frame, 
            "Quiz complete! Your score: " + userScore + "/" + questions.size(),
            "Quiz Finished",
            JOptionPane.INFORMATION_MESSAGE);

   
        currentQuestionIndex = 0;
        userScore = 0;
        frame.dispose();
    }
    
    /**
     * Afiseaza clasamentul intr-o fereastra separata.
     */
    
    public static void displayLeaderboard() {
        JFrame leaderboardFrame = new JFrame("Leaderboard");
        leaderboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        leaderboardFrame.setSize(400, 600);

        List<Pair<String, Integer>> scores = DatabaseUtils.getTopScores();
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Pair<String, Integer> score : scores) {
            model.addElement(score.getKey() + " - " + score.getValue());
        }

        JList<String> list = new JList<>(model);
        leaderboardFrame.add(new JScrollPane(list));

        leaderboardFrame.setVisible(true);
    }

    

}
