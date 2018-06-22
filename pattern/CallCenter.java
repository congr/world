public class CallCenter {

    public static void main(String[] args) {
        ResponsibilityHandler handler = new ResponsibilityHandler();
        handler.setDispatcher();

        for (int i = 0; i < 9; i++) {
            handler.getCall();
        }
    }

    static class ResponsibilityHandler {
        Employee respondent = new Respondent();
        Employee manager = new Manager();
        Employee director = new Director();

        ResponsibilityHandler() {

        }

        void setDispatcher () {
            respondent.setNext(manager);
            manager.setNext(director);
        }

        void getCall() {
            respondent.pickup();
        }
    }

    static abstract class Employee {
        Employee next;

        Employee() {

        }

        void setNext(Employee next) {
            this.next = next;
        }

        abstract void pickup();
        abstract void hangup();
    }

    static class Respondent extends Employee {
        int cnt;

        @Override
        void pickup() {
            if (cnt>2) {
                next.pickup();
                return;
            }
            cnt++;
            System.out.println("Respondent gets up the call");
        }

        @Override
        void hangup() {
            cnt--;
            System.out.println("Respondent hang up the call");
        }
    }

    static class Manager extends Employee {
        int cnt;

        @Override
        void pickup() {
            if (cnt>2) {
                next.pickup();
                return;
            }
            cnt++;
            System.out.println("Manager gets up the call");
        }

        @Override
        void hangup() {
            cnt--;
            System.out.println("Manager hang up the call");
        }
    }

    static class Director extends Employee {
        int cnt;

        @Override
        void pickup() {
            cnt++;
            System.out.println("Director gets up the call");
        }

        @Override
        void hangup() {
            cnt--;
            System.out.println("Director hang up the call");
        }
    }
}
