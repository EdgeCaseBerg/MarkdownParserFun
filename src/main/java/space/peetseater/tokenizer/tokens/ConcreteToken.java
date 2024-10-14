package space.peetseater.tokenizer.tokens;

public class ConcreteToken extends AbstractToken {

    public static ConcreteToken make(String type, String value) {
        return switch (type) {
            case TextToken.TYPE             -> new TextToken(value);
            case UnderscoreToken.TYPE       -> UnderscoreToken.INSTANCE;
            case StarToken.TYPE             -> StarToken.INSTANCE;
            case NewLineToken.TYPE          -> NewLineToken.INSTANCE;
            case DashToken.TYPE             -> DashToken.INSTANCE;
            case BracketStartToken.TYPE     -> BracketStartToken.INSTANCE;
            case BracketEndToken.TYPE       -> BracketEndToken.INSTANCE;
            case ParenStartToken.TYPE       -> ParenStartToken.INSTANCE;
            case ParenStopToken.TYPE        -> ParenStopToken.INSTANCE;
            case PoundToken.TYPE            -> PoundToken.INSTANCE;
            case EqualsToken.TYPE           -> EqualsToken.INSTANCE;
            case BacktickToken.TYPE         -> BacktickToken.INSTANCE;
            case AngleStartToken.TYPE       -> AngleStartToken.INSTANCE;
            case AngleStopToken.TYPE        -> AngleStopToken.INSTANCE;
            case EscapeCharacterToken.TYPE  -> EscapeCharacterToken.INSTANCE;
            case ColonToken.TYPE            -> ColonToken.INSTANCE;
            default                         -> new ConcreteToken(type, value);
        };
    }


    protected ConcreteToken(String type, String value) {
        super(type, value);
        assert(type != null);
        assert(value != null);
    }

    public int length() {
        return value.length();
    }

    public boolean isNull() {
        return false;
    }

    public boolean isPresent() {
        return true;
    }
}
