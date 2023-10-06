import java.util.ArrayList;
import java.util.Scanner;

// Класс животного с инкапсуляцией методов
class Animal {
    private String name;
    private String birthdate;
    private ArrayList<String> commands;

    public Animal(String name, String birthdate) {
        this.name = name;
        this.birthdate = birthdate;
        this.commands = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public ArrayList<String> getCommands() {
        return commands;
    }

    public void train(String command) {
        commands.add(command);
    }
}

// Класс для учета числа новых животных
class Counter implements AutoCloseable {
    private int count = 0;

    public void add() {
        count++;
    }

    public int getCount() {
        return count;
    }

@Override
    public void close() throws Exception {
        if (count > 0) {
            throw new Exception("Счетчик не закрыт");
        }
    }
}

 class AnimalRegistry {
    public static void main(String[] args) {
        ArrayList<Animal> animals = new ArrayList<>();
        try (Counter counter = new Counter(); Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nМеню:");
                System.out.println("1. Завести новое животное");
                System.out.println("2. Определить тип животного");
                System.out.println("3. Показать список команд для животного");
                System.out.println("4. Обучить животное новой команде");
                System.out.println("5. Выйти");

                System.out.print("Выберите действие: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Введите имя животного: ");
                        String name = scanner.nextLine();
                        System.out.print("Введите дату рождения (гггг-мм-дд): ");
                        String birthdate = scanner.nextLine();

                        Animal animal = new Animal(name, birthdate);
                        animals.add(animal);
                        counter.add();
                        System.out.println(name + " успешно зарегистрировано в реестре.");
                        break;

                    case 2:
                        System.out.print("Введите имя животного: ");
                        String searchName = scanner.nextLine();
                        boolean found = false;
                        for (Animal a : animals) {
                            if (a.getName().equals(searchName)) {
                                found = true;
                                System.out.println(searchName + " - ");
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println(searchName + " не найдено в реестре.");
                        }
                        break;

                    case 3:
                        System.out.print("Введите имя животного: ");
                        String animalName = scanner.nextLine();
                        for (Animal a : animals) {
                            if (a.getName().equals(animalName)) {
                                ArrayList<String> commands = a.getCommands();
                                if (commands.isEmpty()) {
                                    System.out.println(animalName + " не знает ни одной команды.");
                                } else {
                                    System.out.println("Список команд для " + animalName + ":");
                                    for (String command : commands) {
                                        System.out.println("- " + command);
                                    }
                                }
                                break;
                            }
                        }
                        break;

                    case 4:
                        System.out.print("Введите имя животного: ");
                        String animalNameToTrain = scanner.nextLine();
                        System.out.print("Введите новую команду: ");
                        String newCommand = scanner.nextLine();
                        for (Animal a : animals) {
                            if (a.getName().equals(animalNameToTrain)) {
                                a.train(newCommand);
                                System.out.println(animalNameToTrain + " успешно обучено команде: " + newCommand);
                                break;
                            }
                        }
                        break;

                    case 5:
                        return;

                    default:
                        System.out.println("Некорректный ввод. Попробуйте снова.");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }


}
