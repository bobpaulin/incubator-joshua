package joshua.decoder.ff.tm.hiero;

import java.util.logging.Logger;

import joshua.corpus.SymbolTable;
import joshua.decoder.ff.FeatureFunctionList;
import joshua.decoder.ff.tm.BilingualRule;
import joshua.decoder.ff.tm.GrammarReader;

public class HieroFormatReader extends GrammarReader<BilingualRule> {

	private static final Logger logger = Logger
			.getLogger(HieroFormatReader.class.getName());

	static {
		fieldDelimiter = "\\s+\\|{3}\\s+";
		nonTerminalRegEx = "^\\[[A-Z]+\\,[0-9]*\\]$";
		nonTerminalCleanRegEx = "[\\[\\]\\,0-9]+";
		description = "Original Hiero format";
	}
	
	public HieroFormatReader(String grammarFile, SymbolTable vocabulary,
			FeatureFunctionList features) {
		super(grammarFile, vocabulary, features);
	}

	@Override
	protected BilingualRule parseLine(String line) {
		String[] fields = line.split(fieldDelimiter);
		if (fields.length != 4) {
			logger.severe("Rule line does not have four fields: " + line);
		}

		int lhs = symbolTable.addNonterminal(cleanNonTerminal(fields[0]));

		int arity = 0;
		// foreign side
		String[] foreignWords = fields[1].split("\\s+");
		int[] french = new int[foreignWords.length];
		for (int i = 0; i < foreignWords.length; i++) {
			if (isNonTerminal(foreignWords[i])) {
				arity++;
				french[i] = symbolTable.addNonterminal(foreignWords[i]);
			} else {
				french[i] = symbolTable.addTerminal(foreignWords[i]);
			}
		}

		// english side
		String[] englishWords = fields[2].split("\\s+");
		int[] english = new int[englishWords.length];
		for (int i = 0; i < englishWords.length; i++) {
			if (isNonTerminal(englishWords[i])) {
				english[i] = symbolTable.addNonterminal(englishWords[i]);
			} else {
				english[i] = symbolTable.addTerminal(englishWords[i]);
			}
		}

		// feature scores
		String[] scores = fields[3].split("\\s+");
		float[] feature_scores = new float[this.features.length];
		int i = 0;
		for (int column : this.features) {
			feature_scores[i++] = Float.parseFloat(scores[column]);
		}

		return new BilingualRule(lhs, french, english, feature_scores, arity);
	}


	@Override
	public String toTokenIds(BilingualRule rule) {
		StringBuffer sb = new StringBuffer("[");
		sb.append(rule.getLHS());
		sb.append("] ||| ");
		sb.append(rule.getFrench());
		sb.append(" ||| ");
		sb.append(rule.getEnglish());
		sb.append(" |||");

		float[] feature_scores = rule.getFeatureScores();
		for (int i = 0; i < feature_scores.length; i++) {
			sb.append(String.format(" %.4f", feature_scores[i]));
		}
		return sb.toString();
	}

	@Override
	public String toTokenIdsWithoutFeatureScores(BilingualRule rule) {
		StringBuffer sb = new StringBuffer("[");
		sb.append(rule.getLHS());
		sb.append("] ||| ");
		sb.append(rule.getFrench());
		sb.append(" ||| ");
		sb.append(rule.getEnglish());
		return sb.toString();
	}

	@Override
	public String toWords(BilingualRule rule) {
		StringBuffer sb = new StringBuffer("[");
		sb.append(rule.getLHS());
		sb.append("] ||| ");
		sb.append(symbolTable.getWords(rule.getFrench()));
		sb.append(" ||| ");
		sb.append(symbolTable.getWords(rule.getEnglish()));
		sb.append(" |||");

		float[] feature_scores = rule.getFeatureScores();
		for (int i = 0; i < feature_scores.length; i++) {
			sb.append(String.format(" %.4f", feature_scores[i]));
		}
		return sb.toString();
	}

	@Override
	public String toWordsWithoutFeatureScores(BilingualRule rule) {
		StringBuffer sb = new StringBuffer("[");
		sb.append(rule.getLHS());
		sb.append("] ||| ");
		sb.append(symbolTable.getWords(rule.getFrench()));
		sb.append(" ||| ");
		sb.append(symbolTable.getWords(rule.getEnglish()));
		sb.append(" |||");
	
		return sb.toString();
	}
}
