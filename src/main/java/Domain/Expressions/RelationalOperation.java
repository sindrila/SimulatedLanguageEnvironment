package Domain.Expressions;

public enum RelationalOperation {
    EQUAL {
        @Override
        public String toString() {
            return "==";
        }
    },
    NOT_EQUAL {
        @Override
        public String toString() {
            return "!=";
        }
    },
    LESS_THAN {
        @Override
        public String toString() {
            return "<";
        }
    },
    LESS_THAN_OR_EQUAL {
        @Override
        public String toString() {
            return "<=";
        }
    },
    GREATER_THAN {
        @Override
        public String toString() {
            return ">";
        }
    },
    GREATER_THAN_OR_EQUAL {
        @Override
        public String toString() {
            return ">=";
        }
    }
}
