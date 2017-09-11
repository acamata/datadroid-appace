package it.unive.dais.cevid.datadroid.lib.util;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DataManipulation {

    public static <T> void filter(@NonNull List<T> l, @NonNull Function<T, Boolean> f) {
        Collection<T> c = new ArrayList<>();
        for (T x : l) {
            if (f.eval(x)) c.add(x);
        }
        l.retainAll(c);
    }

    public static <T> double sumBy(@NonNull List<T> l, @NonNull Function<T, Double> f) {
        double r = 0.;
        for (T x : l) {
            r += f.eval(x);
        }
        return r;
    }

    public static <T> void filterByCode(@NonNull List<T> l, final int code, @NonNull final Function<T, Integer> getCode) {
        filter(l, new Function<T, Boolean>() {
            @Override
            public Boolean eval(T x) {
                return getCode.eval(x) == code;
            }
        });
    }


    public static <T> void filterByWords(@NonNull List<T> l, @NonNull final Collection<CharSequence> ss, @NonNull final Function<T, String> getText) {
        filter(l, new Function<T, Boolean>() {
            @Override
            public Boolean eval(T x) {
                final String s0 = getText.eval(x);
                for (CharSequence s : ss) {
                    if (s0.contains(s)) return true;
                }
                return false;
            }
        });
    }

    public static <T> void filterByWords(@NonNull List<T> l, @NonNull CharSequence[] ss, @NonNull Function<T, String> getText) {
        filterByWords(l, Arrays.asList(ss), getText);
    }

//    public static void prova() {
//        List<SoldiPubbliciParser.Data> l = new ArrayList<>();
//
//        filterByWords(l, new String[]{"pizza", "fichi"}, new Function<SoldiPubbliciParser.Data, String>() {
//            @Override
//            public String eval(SoldiPubbliciParser.Data x) {
//                return x.descrizione_ente;
//            }
//        });
//
//        filterByWords(l, new String[]{"pizza", "fichi"}, new Function<SoldiPubbliciParser.Data, String>() {
//            @Override
//            public String eval(SoldiPubbliciParser.Data x) {
//                return x.descrizione_codice;
//            }
//        });
//    }
}
