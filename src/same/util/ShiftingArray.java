package same.util;

public class ShiftingArray
{
    private final Object[] m_array;
    private int m_index;
    private boolean m_full = false;

    public ShiftingArray(int a_size)
    {
        m_array = new Object[a_size];
        m_index = 0;
    }

    public void append(Object a_object)
    {
        m_array[m_index] = a_object;
        m_index = (m_index + 1) % m_array.length;
        m_full = m_full || (m_index == 0);
    }

    public Object getOldest()
    {
        return m_array[m_index];
    }

    //TODO: Fix for an array of size 0?
    public boolean isFull()
    {
        return m_full;
    }
}
