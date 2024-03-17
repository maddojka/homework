package com.soroko.project.fitness;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Fitness {
    private final int ZONE_SIZE = 20;
    private final String GYM_ZONE = "тренажерный зал";
    private final String POOL_ZONE = "бассейн";
    private final String GROUP_ZONE = "групповые занятия";
    private final String GYM_IS_FULL = "В тренажерном зале нет свободных мест";
    private final String POOL_IS_FULL = "В бассейне нет свободных мест";
    private final String GROUP_IS_FULL = "На групповых занятиях нет свободных мест";
    private final LocalDate CURR_DATE = LocalDate.now();
    private final LocalTime CURR_TIME = LocalTime.now();
    private final LocalDateTime CURR_DATE_TIME = LocalDateTime.now();
    private final static LocalDate MIN_DATE = LocalDate.MIN;
    private Subscription[] gymZone = new Subscription[ZONE_SIZE];
    private Subscription[] poolZone = new Subscription[ZONE_SIZE];
    private Subscription[] groupZone = new Subscription[ZONE_SIZE];

    public Subscription[] getGymZone() {
        return gymZone;
    }

    public Subscription[] getPoolZone() {
        return poolZone;
    }

    public Subscription[] getGroupZone() {
        return groupZone;
    }

    private void addToGymZone(Subscription subscription) {
        if (subscription.getAccess() < 1 || subscription.getAccess() > 3) {
            System.out.println("Нет доступа к данной группе");
            return;
        }

        boolean isFull = false;
        for (int i = 0; i < gymZone.length; i++) {
            isFull = true;
            if (gymZone[i] == null) {
                gymZone[i] = subscription;
                isFull = false;
                System.out.println(subscription.personData.getSurname() + " " + subscription.personData.getName() + " " + GYM_ZONE);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd:MM:yyyy H:mm:ss");
                String text = dtf.format(CURR_DATE_TIME);
                System.out.println(text);
                break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (gymZone[gymZone.length - 1] != null && isFull) System.out.println(GYM_IS_FULL);
    }

    private void addToPoolZone(Subscription subscription) {
        if (subscription.getAccess() != 1 && subscription.getAccess() != 3) {
            System.out.println("Нет доступа к данной группе");
            return;
        }
        boolean isFull = false;
        for (int i = 0; i < poolZone.length; i++) {
            isFull = true;
            if (poolZone[i] == null) {
                poolZone[i] = subscription;
                isFull = false;
                System.out.println(subscription.personData.getSurname() + " " + subscription.personData.getName() + " " + POOL_ZONE);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd:MM:yyyy H:mm:ss");
                String text = dtf.format(CURR_DATE_TIME);
                System.out.println(text);
                break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (poolZone[poolZone.length - 1] != null && isFull) System.out.println(POOL_IS_FULL);
    }

    private void addToGroupZone(Subscription subscription) {
        if (subscription.getAccess() != 2 && subscription.getAccess() != 3) {
            System.out.println("Нет доступа к данной группе");
            return;
        }
        boolean isFull = false;
        for (int i = 0; i < groupZone.length; i++) {
            isFull = true;
            if (groupZone[i] == null) {
                groupZone[i] = subscription;
                isFull = false;
                System.out.println(subscription.personData.getSurname() + " " + subscription.personData.getName() + " " + GROUP_ZONE);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd:MM:yyyy H:mm:ss");
                String text = dtf.format(CURR_DATE_TIME);
                System.out.println(text);
                break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (groupZone[groupZone.length - 1] != null && isFull) System.out.println(GROUP_IS_FULL);
    }

    public void addToDesiredZone(String zone, Subscription subscription) {
        for (int i = 0; i < ZONE_SIZE; i++) {
            if (subscription.equals(gymZone[i])
                    || subscription.equals(poolZone[i]) || subscription.equals(groupZone[i])) {
                System.out.println("Данный пользователь уже зарегистрирован в одной из групп");
                return;
            }
        }

        if (subscription.getDateOfExpiration().isBefore(CURR_DATE)
                && !subscription.getDateOfExpiration().isEqual(CURR_DATE)) {
            System.out.println("Срок действия абонемента истек");
            return;
        }

        if (CURR_TIME.isBefore(subscription.getStartTimeOfVisit())
                || CURR_TIME.isAfter(subscription.getEndTimeOfVisit())) {
            System.out.println("Абонемент не активен в данное время суток");
            return;
        }

        if ((zone.equalsIgnoreCase(GYM_ZONE))) {
            addToGymZone(subscription);
        } else if ((zone.equalsIgnoreCase(POOL_ZONE))) {
            addToPoolZone(subscription);
        } else if ((zone.equalsIgnoreCase(GROUP_ZONE))) {
            addToGroupZone(subscription);
        } else {
            System.out.println("Введите правильную зону");
        }
    }

    public void printGroups() {
        System.out.println("Посетители тренажерного зала:");
        System.out.println(Arrays.toString(getGymZone()));
        System.out.println("Посетители бассейна:");
        System.out.println(Arrays.toString(getPoolZone()));
        System.out.println("Посетители групповых занятий:");
        System.out.println(Arrays.toString(getGroupZone()));
    }

    public static void initializeZone(Subscription[] subscriptions) {
        PersonData defaultPerson = new PersonData("Пользователь", "Неизвестный", 1970);
        Subscription initialSubscription = new Subscription
                (-1, defaultPerson, TypeOfSubscription.DEFAULT, LocalDate.MIN, LocalDate.MAX);
        for (int i = 0; i < subscriptions.length; i++) {
            subscriptions[i] = initialSubscription;
        }
    }
}
