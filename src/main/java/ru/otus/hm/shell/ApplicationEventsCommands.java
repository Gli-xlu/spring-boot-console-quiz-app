package ru.otus.hm.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.hm.dao.Student;
import ru.otus.hm.service.QuizService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationEventsCommands {
    private Student student;

    private final QuizService quizService;

    @ShellMethod(value = "Help command", key = {"h", "help"})
    public String help() {
        return """
                Команды:
                Логин:
                    Варианты написания: l, login
                    Аргументы: имя, фамилия
                    Пример: login Имя Фамилия
                Тест:
                    Варианты написания: t, test
                    Аргументы: -
                    Пример: test
                """;
    }

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(String name, String surname) {
        student = new Student(name, surname);
        return String.format("Добро пожаловать: %s %s", surname, name);
    }

    @ShellMethod(value = "Test student command", key = {"t", "test"})
    @ShellMethodAvailability(value = "isTestCommandAvailable")
    public String test() {
        return quizService.testStudent(student);
    }

    @ShellMethod(value = "Print test command", key = {"p", "print"})
    @ShellMethodAvailability(value = "isTestCommandAvailable")
    public String printTest() {
        return quizService.printQuiz();
    }

    private Availability isTestCommandAvailable() {
        return student == null ? Availability.unavailable("Сначала залогиньтесь") : Availability.available();
    }
}
