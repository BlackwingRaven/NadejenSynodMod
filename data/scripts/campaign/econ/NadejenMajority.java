package data.scripts.campaign.econ;

import com.fs.starfarer.api.impl.campaign.econ.BaseMarketConditionPlugin;
import com.fs.starfarer.api.impl.campaign.econ.ConditionData;
import java.util.Arrays;


public class NadejenMajority extends BaseMarketConditionPlugin {

	private static final String [] SynodFactions = new String [] {
		"nadejensinod",
	};

    /**
     *
     * @param id
     */
    @Override
	public void apply(String id) {
		if (Arrays.asList(SynodFactions).contains(market.getFactionId())) {
			market.getStability().modifyFlat(id, ConditionData.STABILITY_LUDDIC_MAJORITY_BONUS, "Nadejen Fellowship");
		} else {
			market.getStability().modifyFlat(id, ConditionData.STABILITY_LUDDIC_MAJORITY_PENALTY * 3, "Nadejen Insurgency");
		}

	}

    /**
     *
     * @param id
     */
    @Override
	public void unapply(String id) {
		market.getStability().unmodify(id);
		
	}

}
