package com.soroko.project.textQuest;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Menu {
    private boolean gameIsOn;
    private boolean gameIsActive;
    private boolean gamePaused;
    private boolean gameSaved;
    private String titleOfChapter;
    private QuestStateMachine questStateMachine;

    public Menu(QuestStateMachine questStateMachine) {
        this.questStateMachine = questStateMachine;
    }

    public boolean getGameIsOn() {
        return gameIsOn;
    }

    public boolean getGameIsActive() {
        return gameIsActive;
    }

    public boolean getGamePaused() {
        return gamePaused;
    }

    public boolean getGameSaved() {
        return gameSaved;
    }

    public String getTitleOfChapter() {
        return titleOfChapter;
    }

    public void setTitleOfChapter(String titleOfChapter) {
        this.titleOfChapter = titleOfChapter;
    }

    public QuestStateMachine getQuestStateMachine() {
        return questStateMachine;
    }

    public void setQuestStateMachine(QuestStateMachine questStateMachine) {
        this.questStateMachine = questStateMachine;
    }

    public void startGame() {
        this.gameIsOn = true;
        this.gameIsActive = true;
    }

    public void returnGame() {
        this.gameIsActive = true;
        this.gamePaused = false;
    }

    public void exitGame() {
        System.exit(0);
    }

    public void saveGame() {
        gameSaved = true;
        try (PrintWriter out = new PrintWriter("quest.txt")) {
            out.print(this.titleOfChapter);
        } catch (IOException ex) {
            System.out.println("Ошибка при попытке чтения в файл");
        }
    }

    public void loadGame() {
        try {
            titleOfChapter = new String(Files.readAllBytes(Paths.get("quest.txt")));
        } catch (IOException | NullPointerException ex) {
            System.out.println("Ошибка при попытке чтения из файла");
        }

        switch (titleOfChapter) {
            case "Introduction" -> questStateMachine = QuestStateMachine.Introduction;
            case "HappyEnd" -> questStateMachine = QuestStateMachine.HappyEnd;
            case "TryToSearch" -> questStateMachine = QuestStateMachine.TryToSearch;
            case "TryToAskLocals" -> questStateMachine = QuestStateMachine.TryToAskLocals;
            case "UnhappyEndSearch" -> questStateMachine = QuestStateMachine.UnhappyEndSearch;
            case "TryToAskTheOwl" -> questStateMachine = QuestStateMachine.TryToAskTheOwl;
            case "TryToAskTheWolf" -> questStateMachine = QuestStateMachine.TryToAskTheWolf;
            case "TrustTheOwl" -> questStateMachine = QuestStateMachine.TrustTheOwl;
            case "FindTheHoney" -> questStateMachine = QuestStateMachine.FindTheHoney;
            case "UnhappyEndSteal" -> questStateMachine = QuestStateMachine.UnhappyEndSteal;
            case "WaitForTheBees" -> questStateMachine = QuestStateMachine.WaitForTheBees;
            case "UnhappyEndRest" -> questStateMachine = QuestStateMachine.UnhappyEndRest;
            case "BringTheHoneyToTheBear" -> questStateMachine = QuestStateMachine.BringTheHoneyToTheBear;
            default -> throw new IllegalArgumentException("Неверное наименование главы");
        }
    }

    public void returnMenu() {
        gameIsActive = false;
        gamePaused = true;
    }

    public void printMenu() {
        if (!gameIsOn) {
            System.out.println("1. Начать игру");
            System.out.println("3. Выйти из игры");

        } else if (gameSaved) {
            System.out.println("2. Вернуться к игре");
            System.out.println("3. Выйти из игры");
            System.out.println("4. Сохранить игру");
            System.out.println("5. Загрузить игру");
        } else if (gameIsOn) {
            System.out.println("2. Вернуться к игре");
            System.out.println("3. Выйти из игры");
            System.out.println("4. Сохранить игру");
        }
    }


}
