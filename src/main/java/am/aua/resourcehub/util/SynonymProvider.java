package am.aua.resourcehub.util;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.dictionary.Dictionary;
import net.sf.extjwnl.data.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utility class used for expanding a user query with synonyms
 */
public class SynonymProvider {
    private final Dictionary dictionary;
    private final Map<String, List<String>> manualSynonyms;


    public SynonymProvider() throws JWNLException {
        dictionary = Dictionary.getDefaultResourceInstance();

        manualSynonyms = new HashMap<String, List<String>>(); //add any manual synonyms here if needed
    }

    /**
     * Get synonyms from the dictionary
     * @param term the term for which to get synonyms
     * @return a Set of synonyms including the term itself
     * @throws JWNLException
     */
    public Set<String> getSynonyms(String term) throws JWNLException {
        Set<String> result = new HashSet<String>();
        result.add(term);

        //add manual synonyms if any
        List<String> manual = manualSynonyms.get(term.toLowerCase());
        if (manual != null)
            result.addAll(manual);

        IndexWord word = dictionary.lookupIndexWord(POS.NOUN, term);
        if (word != null) {
            for (Synset synset : word.getSenses()) {
                for (Word w : synset.getWords()) {
                    result.add(w.getLemma().replace('_', ' '));
                }
            }
        }

        return result;
    }
}
