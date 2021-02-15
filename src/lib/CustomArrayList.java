package lib;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomArrayList<E> extends ArrayList<E>
{
    private static final long serialVersionUID = 1L;
    private HashMap <String, E> keySets = new HashMap<String, E>();

    public void add(String identifier, E e)
    {
        keySets.put(identifier, e);
        super.add(e);
    }

    public E get(String identifier)
    {
        return keySets.get(identifier);
    }
}