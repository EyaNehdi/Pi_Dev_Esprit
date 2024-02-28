package services;

import entities.Quizz;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizzService implements IService<Quizz> {
    Connection connection;

    public QuizzService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void add(Quizz quizz) throws SQLException {
        String req = "INSERT INTO quizz (question, option1, option2, option3, option4, correctAnswer) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, quizz.getQuestion());
            stmt.setString(2, quizz.getOption1());
            stmt.setString(3, quizz.getOption2());
            stmt.setString(4, quizz.getOption3());
            stmt.setString(5, quizz.getOption4());
            stmt.setString(6, quizz.getCorrectAnswer());
            int result = stmt.executeUpdate();
            if (result > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        quizz.setId_quizz(generatedKeys.getInt(1));
                        System.out.println("Enregistrement ajouté avec succès. ID généré : " + quizz.getId_quizz());
                    } else {
                        System.out.println("Échec de la récupération de l'ID généré après l'ajout.");
                    }
                }
            } else {
                System.out.println("Aucun enregistrement ajouté.");
            }
        }
    }

    @Override
    public void delete(Quizz quizz) throws SQLException {
        try {
            String req = "DELETE FROM quizz WHERE id_quizz=?";
            try (PreparedStatement pst = connection.prepareStatement(req)) {
                pst.setInt(1, quizz.getId_quizz());
                int result = pst.executeUpdate();
                if (result > 0) {
                    System.out.println("Suppression réussie.");
                } else {
                    System.out.println("Aucun enregistrement supprimé.");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression : " + ex.getMessage());
        }
    }

    @Override
    public void update(Quizz quizz) throws SQLException {
        try {
            String req = "UPDATE quizz SET question=?, option1=?, option2=?, option3=?, option4=?, correctAnswer=? WHERE id_quizz=?";
            try (PreparedStatement pst = connection.prepareStatement(req)) {
                pst.setString(1, quizz.getQuestion());
                pst.setString(2, quizz.getOption1());
                pst.setString(3, quizz.getOption2());
                pst.setString(4, quizz.getOption3());
                pst.setString(5, quizz.getOption4());
                pst.setString(6, quizz.getCorrectAnswer());
                pst.setInt(7, quizz.getId_quizz());
                int result = pst.executeUpdate();
                if (result > 0) {
                    System.out.println("Mise à jour réussie.");
                } else {
                    System.out.println("Aucun enregistrement mis à jour.");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la mise à jour : " + ex.getMessage());
        }
    }

    @Override
    public List<Quizz> readAll() throws SQLException {
        List<Quizz> quizzList = new ArrayList<>();
        String req = "SELECT * FROM quizz";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Quizz q = new Quizz();
                q.setId_quizz(rs.getInt("id_quizz"));
                q.setQuestion(rs.getString("question"));
                q.setOption1(rs.getString("option1"));
                q.setOption2(rs.getString("option2"));
                q.setOption3(rs.getString("option3"));
                q.setOption4(rs.getString("option4"));
                q.setCorrectAnswer(rs.getString("correctAnswer"));
                quizzList.add(q);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la lecture de tous les enregistrements : " + e.getMessage());
        }
        return quizzList;
    }

    @Override
    public Quizz readById(int id_quizz) throws SQLException {
        String requete = "SELECT * FROM quizz WHERE id_quizz=?";
        try (PreparedStatement pst = connection.prepareStatement(requete)) {
            pst.setInt(1, id_quizz);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Quizz quizz = new Quizz();
                    quizz.setId_quizz(rs.getInt("id_quizz"));
                    quizz.setOption1(rs.getString("option1"));
                    quizz.setOption2(rs.getString("option2"));
                    quizz.setOption3(rs.getString("option3"));
                    quizz.setOption4(rs.getString("option4"));
                    quizz.setCorrectAnswer(rs.getString("correctAnswer"));
                    return quizz;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la lecture par ID : " + e.getMessage());
        }
        return null;
    }

    public List<Quizz> getRandomQuizzQuestions(int numberOfQuestions) {
        List<Quizz> quizzQuestions = new ArrayList<>();
        String req = "SELECT * FROM quizz ORDER BY RAND() LIMIT ?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, numberOfQuestions);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Quizz q = new Quizz();
                    q.setId_quizz(rs.getInt("id_quizz"));
                    q.setQuestion(rs.getString("question"));
                    q.setOption1(rs.getString("option1"));
                    q.setOption2(rs.getString("option2"));
                    q.setOption3(rs.getString("option3"));
                    q.setOption4(rs.getString("option4"));
                    q.setCorrectAnswer(rs.getString("correctAnswer"));
                    quizzQuestions.add(q);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzQuestions;
    }

    public boolean quizzExists(String question) {
        String req = "SELECT COUNT(*) FROM quizz WHERE question=?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setString(1, question);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Quizz findById(int id_quizz) throws SQLException {
        String requete = "SELECT * FROM quizz WHERE id_quizz=?";
        try (PreparedStatement pst = connection.prepareStatement(requete)) {
            pst.setInt(1, id_quizz);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Quizz quizz = new Quizz();
                    quizz.setId_quizz(rs.getInt("id_quizz"));
                    quizz.setQuestion(rs.getString("question"));
                    quizz.setOption1(rs.getString("option1"));
                    quizz.setOption2(rs.getString("option2"));
                    quizz.setOption3(rs.getString("option3"));
                    quizz.setOption4(rs.getString("option4"));
                    quizz.setCorrectAnswer(rs.getString("correctAnswer"));
                    return quizz;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la lecture par ID : " + e.getMessage());
        }
        return null;
    }
}
