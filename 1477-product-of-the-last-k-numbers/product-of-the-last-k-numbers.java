


class ProductOfNumbers {

    private List<Integer> l;

    public ProductOfNumbers() {
        this.l = new ArrayList<>();
    }

    public void add(int num) {
        if (num == 0) {
            l.clear();
            return;
        }

        int size = l.size();
        if (size == 0) {
            l.add(num);
        } else {
            l.add(num * l.get(size - 1));
        }
    }

    public int getProduct(int k) {
        int size = l.size();

        if (k > size) {
            return 0;
        } else if (k == size) {
            return l.get(size - 1);
        } else {
            return l.get(size - 1) / l.get(size - 1 - k);
        }
    }
}

