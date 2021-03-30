package LinkedList;

import java.util.*;
import java.lang.reflect.Array;

public class LinkedList<T> implements List<T> {

    private Item<T> firstInList = null;
    private Item<T> lastInList = null;
    private int size;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(final Object o) {
        for (Item<T> x = firstInList; x != null; x = x.getNextItem()) {
            if (o == null) {
                if (x.element == null) {
                    return true;
                }
            } else {
                if (x.element == null) {
                    continue;
                } else {
                    if (o.equals(x.element)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new ElementsIterator(0);
    }

    @Override
    public Object[] toArray() {
        // BEGIN (write your solution here)
        Object[] m = new Object[size];
        return m;
        // END
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            a = (T1[])Array.newInstance(a.getClass().getComponentType(), size);
        }
        int i = 0;
        Object[] result = a;
        for (Item<T> x = firstInList; x != null; x = x.getNextItem())
            result[i++] = x.element;

        if (a.length > size)
            a[size] = null;

        return a;
    }

    @Override
    public boolean add(final T newElement) {
        if (size() == 0) { // add firstInList element
            firstInList = new Item<>(newElement, null, null);
        } else if (size() == 1) { // add lastInList element
            lastInList = new Item<>(newElement, firstInList, null);
            firstInList.setNextItem(lastInList);
        } else {
            Item<T> addItem = new Item<>(newElement, lastInList, null);
            this.lastInList.setNextItem(addItem);
            this.lastInList = addItem;
        }
        size++;
        return true;
    }

    @Override
    public void add(final int index, final T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(final Object o) {
        if (o == null) {
            for (Item<T> x = firstInList; x != null; x = x.getNextItem()) {
                if (x.getNextItem() == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Item<T> x = firstInList; x != null; x = x.getNextItem()) {
                if (o.equals(x.element)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public T remove(final int index) throws IndexOutOfBoundsException {
        if (index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        int current = 0;
        Item<T> x = firstInList;
        while (index != current) {
            x = x.getNextItem();
            current++;
        }

        return unlink(x);
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        for (final Object item : c) {
            if (!this.contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        for (final T item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean addAll(final int index, final Collection elements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        for (final Object item : c) {
            remove(item);
        }
        return true;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        this.removeIf(item -> !c.contains(item));
        return true;
    }

    @Override
    public void clear() {
        for (Item<T> x = firstInList; x != null;)
        {
            Item<T> next = x.getNextItem();
            x.element = null;
            x.setPrevItem(null);
            x.setNextItem(null);
            x = next;
        }
        firstInList = lastInList = null;
        size = 0;
    }

    @Override
    public List<T> subList(final int start, final int end) {
        return Collections.emptyList();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ElementsIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(final int index) {
        return new ElementsIterator(index);
    }

    @Override
    public int lastIndexOf(final Object target) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(final Object o) {
        int indexOf = 0;
        if (o == null) {
            for (Item<T> x = firstInList; x != null; x = x.getNextItem()) {
                if (x.getNextItem() == null) {
                    return indexOf;
                }
                indexOf++;
            }
        } else {
            for (Item<T> x = firstInList; x != null; x = x.getNextItem()) {
                if (o.equals(x.element)) {
                    return indexOf;
                }
                indexOf++;
            }
        }
        return -1;
    }

    @Override
    public T set(final int index, final T element) throws IndexOutOfBoundsException {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        int indexFind = 0;
        T e = null;
        for (Item<T> x = firstInList; x != null; x = x.getNextItem()) {
            if (indexFind == index) {
                e = x.element;
                x.element = element;
                break;
            }
            indexFind++;
        }
        return e;
    }

    @Override
    public T get(final int index) throws IndexOutOfBoundsException {
        // BEGIN (write your solution here)
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        int counter = 0;
        for (Item<T> x = firstInList; x != null; x = x.getNextItem()) {
            if (counter == index) {
                return x.element;
            }
            counter++;
        }
        return null; // be compiler happy
    }

    private Item<T> getItemByIndex(final int index) {
        if (index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        Item<T> iterOnList = firstInList;
        int counter = 0;
        for (int i = 0; i <= counter; i ++) {
            iterOnList = iterOnList.getNextItem();
        }
        return iterOnList;
    }

    private T unlink(Item<T> x) {
        final T element = x.element;
        final Item<T> next = x.getNextItem();
        final Item<T> prev = x.getPrevItem();

        if (prev == null) {
            firstInList = next;
        } else {
            prev.setNextItem(next);
            x.setPrevItem(null);
        }

        if (next == null) {
            lastInList = prev;
        } else {
            next.setPrevItem(prev);
            x.setNextItem(null);
        }

        x.element = null;
        size--;
        return element;
    }

    private class ElementsIterator implements ListIterator<T> {

        private static final int LAST_IS_NOT_SET = -1;
        private int index;
        private int lastIndex = LAST_IS_NOT_SET;

        ElementsIterator(final int index) {
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return LinkedList.this.size() > index;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastIndex = index;
            index++;
            return LinkedList.this.get(lastIndex);
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            lastIndex = (--index);
            return LinkedList.this.get(lastIndex);
        }

        @Override
        public void add(final T element) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(final T element) {
            if (lastIndex == LAST_IS_NOT_SET) {
                throw new IllegalStateException();
            }
            LinkedList.this.set(lastIndex, element);
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public void remove() {
            if (lastIndex == LAST_IS_NOT_SET) {
                throw new IllegalStateException();
            }
            LinkedList.this.remove(LinkedList.this.get(lastIndex));
            lastIndex = LAST_IS_NOT_SET;
            index--;
        }
    }

    private static class Item<T> {

        private T element;

        private Item<T> nextItem;

        private Item<T> prevItem;

        Item(final T element, final Item<T> prevItem, final Item<T> nextItem) {
            this.element = element;
            this.nextItem = nextItem;
            this.prevItem = prevItem;
        }

        public Item<T> getNextItem() {
            return nextItem;
        }

        public Item<T> getPrevItem() {
            return prevItem;
        }

        public void setNextItem(Item<T> nextItem) {
            this.nextItem = nextItem;
        }

        public void setPrevItem(Item<T> prevItem) {
            this.prevItem = prevItem;
        }
    }
}

