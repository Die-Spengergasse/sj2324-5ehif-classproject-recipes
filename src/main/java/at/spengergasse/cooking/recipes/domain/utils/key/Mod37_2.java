package at.spengergasse.cooking.recipes.domain.utils.key;

class Mod37_2 extends PureSystemCalculator {

    @Override
    protected int getModulus() {
        return 37;
    }

    @Override
    protected int getRadix() {
        return 2;
    }

    @Override
    protected String getCharacterSet() {
        return "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz*";
    }

    @Override
    protected boolean isDoubleCheckDigit() {
        return false;
    }

}