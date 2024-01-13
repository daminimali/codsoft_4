import java.util.*;

class Question {
    private String questionText;
    private List<String> options;
    private int correctOption;

    public Question(String questionText, List<String> options, int correctOption) {
        this.questionText = questionText;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}

class Quiz {
    private List<Question> questions;
    private int currentQuestionIndex;
    private int userScore;

    public Quiz(List<Question> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.userScore = 0;
    }

    public void startQuiz() {
        Scanner scanner = new Scanner(System.in);

        for (Question question : questions) {
            displayQuestion(question);

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("\nTime's up! Moving to the next question.");
                    timer.cancel();
                    timer.purge();
                }
            };
            timer.schedule(task, 15000); // 15 seconds timer

            int userAnswer = getUserAnswer(scanner);

            timer.cancel();
            timer.purge();

            if (userAnswer == question.getCorrectOption()) {
                System.out.println("Correct!\n");
                userScore++;
            } else {
                System.out.println("Incorrect. The correct answer was option " + (question.getCorrectOption() + 1) + ".\n");
            }
        }

        displayResult();
    }

    private void displayQuestion(Question question) {
        System.out.println(question.getQuestionText());

        List<String> options = question.getOptions();
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.println("Enter your answer (1-" + options.size() + ") or enter 0 to exit: ");
    }

    private int getUserAnswer(Scanner scanner) {
        int userAnswer = scanner.nextInt();
        if (userAnswer == 0) {
            System.out.println("Quiz terminated by user.");
            System.exit(0);
        }
        return userAnswer - 1;
    }

    private void displayResult() {
        System.out.println("Quiz completed!");
        System.out.println("Your score: " + userScore + "/" + questions.size());
        System.out.println("Summary of correct/incorrect answers:");

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            System.out.println("Q" + (i + 1) + ": " +
                    (userAnswerIsCorrect(i) ? "Correct" : "Incorrect. Correct option was " + (question.getCorrectOption() + 1)));
        }
    }

    private boolean userAnswerIsCorrect(int questionIndex) {
        return getUserAnswerForQuestion(questionIndex) == questions.get(questionIndex).getCorrectOption();
    }

    private int getUserAnswerForQuestion(int questionIndex) {
        Scanner scanner = new Scanner(System.in);
        displayQuestion(questions.get(questionIndex));
        return getUserAnswer(scanner);
    }
}

public class QuizApplication {
    public static void main(String[] args) {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is the capital of France?", Arrays.asList("Berlin", "Paris", "Madrid", "Rome"), 1));
        questions.add(new Question("Which planet is known as the Red Planet?", Arrays.asList("Venus", "Mars", "Jupiter", "Saturn"), 1));
        questions.add(new Question("What is the largest mammal?", Arrays.asList("Elephant", "Blue Whale", "Giraffe", "Lion"), 1));
        questions.add(new Question("In which year did World War I begin?", Arrays.asList("1914", "1918", "1939", "1945"), 0));
        questions.add(new Question("Who wrote 'Romeo and Juliet'?", Arrays.asList("Charles Dickens", "Jane Austen", "William Shakespeare", "Mark Twain"), 2));

        Quiz quiz = new Quiz(questions);
        quiz.startQuiz();
    }
}