package Domain.Expressions;

public enum ArithmeticOperation {
    ADDITION{
        @Override
        public String toString() {
            return "+";
        }

    },
    SUBTRACTION{
        @Override
        public String toString() {
            return "-";
        }

    },
    MULTIPLICATION{
        @Override
        public String toString() {
            return "*";
        }

    },
    DIVISION{
        @Override
        public String toString() {
            return "/";
        }

    }
}
