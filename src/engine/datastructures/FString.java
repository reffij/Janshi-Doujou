package engine.datastructures;

import java.util.List;

/**
 * {@code FString} is a lightweight formatted string class similar to 
 * {@link java.lang.String#format(String, Object...)}, but supports dynamic 
 * values through {@link java.util.function.Supplier}.
 *
 * <p>Placeholders use {@code {}} syntax and are replaced in order.</p>
 *
  * <p><b>Examples:</b></p>
  *
  * <pre>{@code
  * // Static value
  * new FString("HP: {}", 100);
  *
  * // Dynamic value (updated every toString call)
  * new FString("HP: {}", () -> player.getHealth());
  * }</pre>
  */
public class FString {

    private String s;
    private List<Object> args;

    /**
     * Creates a formatted string with placeholders.
     *
     * @param s format string using {} placeholders
     * @param args values or Suppliers for dynamic evaluation
     */
    public FString(String s, Object... args) {
        this.s = s;
        this.args = List.of(args);
    }

    /**
     * Returns a formatted string with placeholders.
     *
     * @param s format string using {} placeholders
     * @param args values or Suppliers for dynamic evaluation
     * 
     * @return {@code FString}
     */
    public static FString of(String s, Object... args) {
        return new FString(s, args);
    }

    /**
     * Builds the final string by replacing placeholders with arguments.
     * Supplier arguments are evaluated at call time for dynamic values.
     *
     * @return formatted string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(s.length() + 16);

        int i = 0;
        int j = 0;

        while (i < this.s.length()) {
            char c = this.s.charAt(i);
            if (c != '{') {
                sb.append(c);
                i++;
            } else {

                //CASE: last char in string, here we break
                if (i + 1 == s.length()) {
                    sb.append('{');
                    break;
                }

                //CASE: Next char is '}', here is our target case
                else if (this.s.charAt(i + 1) == '}') {

                    if (j < args.size()) {
                        Object arg = args.get(j);

                        if (arg instanceof java.util.function.Supplier<?> supplier) {
                            sb.append(String.valueOf(supplier.get()));
                        } else {
                            sb.append(String.valueOf(arg));
                        }
                        i += 2;
                        j++;
                    } else {
                        throw new IndexOutOfBoundsException("Encountered '{}' but had no argument.");
                    }
                }

                //CASE: Next char is '{', here is exception 1,
                //we add '{' and skip the next '{' so it is as if
                //there was only 1 '{' in the string
                else if (this.s.charAt(i + 1) == '{') {
                    sb.append('{');
                    i += 2;
                }

                //CASE: Next char is neither '}' or '{', here is
                //exception 2, we simply add '{' as if it were a normal
                //char
                else {
                    sb.append('{');
                    i++;
                }
            }
        }

        return sb.toString();
    }
}
