import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.*;
import com.google.gson.Gson;


public class DatabaseUtils {
	
	
	public static void insertQuestion(String question, String option1, String option2, String option3, String option4, int answer) {
        String sql = "INSERT INTO questions(question, option1, option2, option3, option4, answer) VALUES(?,?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:quiz.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, question);
            pstmt.setString(2, option1);
            pstmt.setString(3, option2);
            pstmt.setString(4, option3);
            pstmt.setString(5, option4);
            pstmt.setInt(6, answer);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public static void populateDatabase() {
        // Insert questions
	insertQuestion("Who won the Formula 1 World Championship in 2020?", 
	               "Lewis Hamilton", "Max Verstappen", "Sebastian Vettel", "Valtteri Bottas", 1);

	insertQuestion("Which driver is known for winning seven World Championships?", 
	               "Ayrton Senna", "Michael Schumacher", "Niki Lauda", "Alain Prost", 2);

	insertQuestion("What is the home country of the Red Bull Racing team?", 
	               "Austria", "United Kingdom", "Germany", "Italy", 1);

	insertQuestion("In which city is the Formula 1 night race held?", 
	               "Abu Dhabi", "Monaco", "Singapore", "Melbourne", 3);

	insertQuestion("Who was the youngest ever Formula 1 World Champion?", 
	               "Fernando Alonso", "Sebastian Vettel", "Lewis Hamilton", "Max Verstappen", 2);

	insertQuestion("Which team did Fernando Alonso drive for when he won his World Championships?", 
	               "Ferrari", "McLaren", "Renault", "Red Bull", 3);

	insertQuestion("What color is traditionally associated with British racing cars in Formula 1?", 
	               "Green", "Red", "Blue", "Silver", 1);

	insertQuestion("How many times did Ayrton Senna win the World Championship?", 
	               "3", "5", "4", "2", 1);

	insertQuestion("In which year did Lewis Hamilton make his Formula 1 debut?", 
	               "2007", "2008", "2006", "2005", 1);

	insertQuestion("Which circuit hosts the British Grand Prix?", 
	               "Silverstone", "Monaco", "Spa", "Monza", 1);
	
	insertQuestion("In what year was the first Formula 1 World Championship race?", 
            "1950", "1955", "1960", "1965", 1);

	insertQuestion("Which driver won the most Formula 1 World Championships?", 
	            "Michael Schumacher", "Lewis Hamilton", "Juan Manuel Fangio", "Sebastian Vettel", 2);
	
	insertQuestion("Which team has won the most Constructors' Championships?", 
	            "Ferrari", "McLaren", "Williams", "Mercedes", 1);
	
	insertQuestion("Which country is home to the famous Monza race track?", 
	            "Spain", "Monaco", "Italy", "France", 3);
	
	insertQuestion("What is the color of the Ferrari Formula 1 team?", 
	            "Red", "Blue", "Yellow", "Green", 1);
	
	insertQuestion("Who won his eighth World Championship in 2021?",
				"Lewis Hamilton", "Max Verstappen", "Valtteri Bottas", "Sebastian Vettel", 2);

	insertQuestion("Which team won the Constructors' Championship in the 2020 Formula 1 season?",
				"Mercedes", "Red Bull Racing", "Ferrari", "McLaren", 1);

	insertQuestion("What is the minimum age requirement for a driver to compete in Formula 1?",
				"16 years old", "18 years old", "20 years old", "21 years old", 2);

	insertQuestion("In which year did Formula 1 introduce the hybrid power unit (PU) regulations?",
				"2008", "2010", "2012", "2014", 3);

	insertQuestion("Which circuit is known as the 'Temple of Speed' in Formula 1?",
				"Silverstone Circuit", "Circuit of the Americas", "Monza Circuit", "Circuit de Monaco", 2);

	insertQuestion("Who was the first American driver to win a Formula 1 World Championship?",
				"Mario Andretti", "Phil Hill", "Dan Gurney", "Michael Andretti", 0);

	insertQuestion("Who won the inaugural Formula 1 World Championship in 1950?",
				"Juan Manuel Fangio", "Alberto Ascari", "Giuseppe Farina", "Stirling Moss", 2);

	insertQuestion("What is the maximum number of points a driver can score in a single Formula 1 race?",
				"15 points", "20 points", "25 points", "30 points", 1);

	insertQuestion("Which country hosts the Monaco Grand Prix, one of the most prestigious races in Formula 1?",
				"France", "Italy", "Monaco", "Spain", 2);

	insertQuestion("What is the purpose of the DRS (Drag Reduction System) in Formula 1?",
				"To increase downforce", "To reduce tire wear", "To improve fuel efficiency", "To facilitate overtaking", 3);

	insertQuestion("Which Formula 1 driver is nicknamed 'The Iceman'?",
				"Lewis Hamilton", "Sebastian Vettel", "Kimi Raikkonen", "Max Verstappen", 2);

	insertQuestion("Which team did Michael Schumacher join after his first retirement from Formula 1?",
				"Ferrari", "Mercedes", "Red Bull Racing", "Williams", 1);

	insertQuestion("Who holds the record for the most pole positions in Formula 1?",
				"Lewis Hamilton", "Ayrton Senna", "Michael Schumacher", "Sebastian Vettel", 0);

	insertQuestion("Who was the first female driver to score points in a Formula 1 race?",
				"Maria Teresa de Filippis", "Lella Lombardi", "Susie Wolff", "Giovanna Amati", 0);

	insertQuestion("Which Formula 1 team is known for its distinctive pink livery?",
				"Ferrari", "McLaren", "Alpine", "Aston Martin", 3);

	insertQuestion("What is the length of a typical Formula 1 race in kilometers?",
				"Approximately 300 km", "Approximately 400 km", "Approximately 500 km", "Approximately 600 km", 1);

	insertQuestion("Which driver famously used the helmet design with the 'Halo' in Formula 1?",
				"Lewis Hamilton", "Sebastian Vettel", "Daniel Ricciardo", "Fernando Alonso", 2);

	insertQuestion("In which year did Ayrton Senna tragically lose his life during a Formula 1 race?",
				"1991", "1992", "1993", "1994", 3);
			
	insertQuestion("Which country has produced the most Formula 1 World Champions?",
				"Germany", "United Kingdom", "Brazil", "Finland", 1);

	insertQuestion("Who is the youngest Formula 1 World Champion in history?",
				"Sebastian Vettel", "Lewis Hamilton", "Michael Schumacher", "Max Verstappen", 0);

	insertQuestion("Which team won the very first Formula 1 Constructors' Championship?",
				"Ferrari", "Mercedes", "Williams", "Alfa Romeo", 3);

	insertQuestion("What does DNF stand for in Formula 1?",
				"Did Not Finish", "Disqualified and Fined", "Driver's New Favorite", "Don't Need Fuel", 0);

	insertQuestion("How many laps are there in a typical Formula 1 race?",
				"55 laps", "60 laps", "70 laps", "80 laps", 2);

	insertQuestion("Which driver is known for his nickname 'Honey Badger'?",
				"Daniel Ricciardo", "Lando Norris", "Esteban Ocon", "Pierre Gasly", 0);

	insertQuestion("What is the name of the Formula 1 team owned by Lawrence Stroll?",
				"Aston Martin", "Alpine", "AlphaTauri", "Haas", 0);

	insertQuestion("Which circuit features the famous 'Eau Rouge' corner?",
				"Silverstone Circuit", "Circuit de Barcelona-Catalunya", "Spa-Francorchamps", "Monza Circuit", 2);

	insertQuestion("What is the term for the area where Formula 1 cars are prepared and serviced during a race weekend?",
				"Pit Stop", "Grid", "Paddock", "Parc Fermé", 2);

	insertQuestion("Who is the only driver to win a Formula 1 race with a car running on bioethanol fuel?",
				"Nico Rosberg", "Kimi Raikkonen", "Lewis Hamilton", "Jenson Button", 3);

	insertQuestion("Which Formula 1 team introduced the 'twin-tusk' nose design in the 2014 season?",
				"Mercedes", "Red Bull Racing", "Ferrari", "McLaren", 1);

	insertQuestion("What is the official tire supplier for Formula 1?",
				"Pirelli", "Michelin", "Bridgestone", "Dunlop", 0);

	insertQuestion("Which circuit is known for its unique figure-eight layout?",
				"Monaco Circuit", "Circuit of the Americas", "Suzuka Circuit", "Baku City Circuit", 2);

	insertQuestion("What is the term for the opening lap of a Formula 1 race?",
				"Starting Lap", "Formation Lap", "Green Lap", "Lap 1", 1);

	insertQuestion("Who is the only driver to win a Formula 1 World Championship with Williams?",
				"Nelson Piquet", "Jacques Villeneuve", "Damon Hill", "Nigel Mansell", 3);

	insertQuestion("Which Formula 1 driver is known for his 'Shoey' celebration?",
				"Valtteri Bottas", "Sergio Perez", "Daniel Ricciardo", "Lando Norris", 2);

	insertQuestion("In which year did Formula 1 switch to turbocharged engines with energy recovery systems (ERS)?",
				"2006", "2008", "2010", "2014", 3);

	insertQuestion("Which driver is nicknamed 'Checo'?",
				"Carlos Sainz", "Fernando Alonso", "Sergio Perez", "Esteban Ocon", 2);

	insertQuestion("What is the name of the Formula 1 race held in the principality of Bahrain?",
				"Bahrain Grand Prix", "Qatar Grand Prix", "Abu Dhabi Grand Prix", "Saudi Arabian Grand Prix", 0);

	insertQuestion("Which Formula 1 team is associated with the 'Prancing Horse' logo?",
				"Mercedes", "Red Bull Racing", "Ferrari", "AlphaTauri", 2);


    }
	
	public static List<Question> getRandomQuestions(int numberOfQuestions) {
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
	
	public static void saveScore(String userName, int score) {
	    String sql = "INSERT INTO leaderboard (name, score) VALUES (?, ?)";
	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:quiz.db");
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, userName);
	        pstmt.setInt(2, score);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}
	
	public static List<Pair<String, Integer>> getTopScores() {
        List<Pair<String, Integer>> scores = new ArrayList<>();
        String sql = "SELECT name, score FROM leaderboard ORDER BY score DESC";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:quiz.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                scores.add(new Pair<>(rs.getString("name"), rs.getInt("score")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return scores;
    }
	
	public static void saveGameState(int currentQuestionIndex, int userScore, List<Question> questions) {
	    Gson gson = new Gson();
	    try (FileWriter writer = new FileWriter("gameState.json")) {
	        gson.toJson(new GameState(currentQuestionIndex, userScore, questions), writer);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public static GameState loadGameState() {
	    Gson gson = new Gson();
	    try (FileReader reader = new FileReader("gameState.json")) {
	        return gson.fromJson(reader, GameState.class);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}




}
