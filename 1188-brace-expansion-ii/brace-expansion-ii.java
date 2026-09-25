class Solution {
    int i;

    public List<String> braceExpansionII(String expression) {
        i = 0;
        Set<String> set = parse(expression);
        List<String> ans = new ArrayList<>(set);
        Collections.sort(ans);
        return ans;
    }

    Set<String> parse(String s) {
        Set<String> res = new HashSet<>();
        Set<String> cur = new HashSet<>();
        cur.add("");

        while (i < s.length() && s.charAt(i) != '}') {
            if (s.charAt(i) == ',') {
                res.addAll(cur);
                cur = new HashSet<>();
                cur.add("");
                i++;
            } else {
                Set<String> next = parseTerm(s);
                Set<String> temp = new HashSet<>();

                for (String a : cur) {
                    for (String b : next) {
                        temp.add(a + b);
                    }
                }

                cur = temp;
            }
        }

        res.addAll(cur);

        if (i < s.length() && s.charAt(i) == '}') {
            i++;
        }

        return res;
    }

    Set<String> parseTerm(String s) {
        if (s.charAt(i) == '{') {
            i++;
            return parse(s);
        }

        Set<String> res = new HashSet<>();
        res.add(String.valueOf(s.charAt(i)));
        i++;

        return res;
    }
}