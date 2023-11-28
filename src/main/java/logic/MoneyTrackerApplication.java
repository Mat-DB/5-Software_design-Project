package logic;

public class MoneyTrackerApplication {
    public MoneyTrackerApplication() {
        init();
    }

    public void init() {

    }

    public void run() {
        /*\
         TODO : split run into state machine coupled with buttons/use-cases
                    - create group
                    - add people to group
                    - add ticket : price, person who paid, people who are in debt, how much each debts is, ...
                        - > 'update depts'
                    - update debts of group members
                    - someone pays
                        - > 'update debts'

         */
        testFunctions.testTicketSplit();


    }
}
