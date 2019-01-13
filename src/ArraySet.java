import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

class EvenStringsPred implements Predicate<String> {
  @Override
  public boolean test(String arg0) {
    return arg0.length() % 2 == 0;
  }
}

class LengthMapper implements Function<String, Integer> {
  @Override
  public Integer apply(String arg0) {
    return arg0.length();
  }
}

class LengthAdder implements BiFunction<String, Integer, Integer> {
  @Override
  public Integer apply(String arg0, Integer arg1) {
    return arg1 + arg0.length();
  }
}

public class ArraySet<T> {
  private T[] elements;

  @SuppressWarnings("unchecked")
  public ArraySet() {
    this.elements = (T[]) (new Object[0]);
  }

  private ArraySet(T[] elements) {
    this.elements = elements;
  }

  public ArraySet<T> add(T element) {
    for (int i = 0; i < elements.length; i++)
      if (elements[i].equals(element))
        return this;
    T[] elementsNew = Arrays.copyOf(elements, elements.length + 1);
    elementsNew[elementsNew.length - 1] = element;
    ArraySet<T> newSet = new ArraySet<>(elementsNew);
    return newSet;
  }

  public ArraySet<T> filter(Predicate<T> pred) {
    boolean[] predApps = new boolean[elements.length];
    int count = 0;
    for (int i = 0; i < elements.length; i++)
      if (pred.test(elements[i])) {
        predApps[i] = true;
        count++;
      }
    @SuppressWarnings("unchecked")
    T[] resultElements = (T[]) (new Object[count]);
    int curr = 0;
    for (int i = 0; i < elements.length; i++)
      if (predApps[i])
        resultElements[curr++] = elements[i];
    return new ArraySet<>(resultElements);
  }

  public <U> ArraySet<U> map(Function<T, U> mapFunc) {
    ArraySet<U> resultSet = new ArraySet<>();
    for (T element : elements)
      resultSet = resultSet.add(mapFunc.apply(element));
    return resultSet;
  }

  public <U> U reduce(U init, BiFunction<T, U, U> func) {
    U result = init;
    for (T element : elements)
      result = func.apply(element, result);
    return result;
  }

  @Override
  public String toString() {
    return Arrays.toString(elements);
  }

  public static void main(String[] args) {
    ArraySet<String> stringSet = new ArraySet<String>().add("hallo").add("Pinguin").add("wie").add("geht").add("es")
        .add("?");
    System.out.println("Even strings: " + stringSet.filter(new EvenStringsPred()));
    System.out.println("Set of lengths: " + stringSet.map(new LengthMapper()));
    System.out.println("Total length of all strings: " + stringSet.reduce(0, new LengthAdder()));

    ArraySet<Integer> arraySet = new ArraySet<Integer>().add(0).add(1).add(2).add(1).add(5);
    System.out.println(arraySet);
    ArraySet<Integer> filtered = arraySet.filter(x -> x % 2 == 0);
    System.out.println(filtered);
    ArraySet<String> mapped1 = arraySet.map(x -> "Die Zahl lautet: " + x);
    System.out.println(mapped1);
    ArraySet<String> mapped2 = arraySet.map(x -> "Hallo");
    System.out.println(mapped2);
    System.out.println(arraySet.reduce(0.0, (x, acc) -> acc + x / 10f));

  }
}