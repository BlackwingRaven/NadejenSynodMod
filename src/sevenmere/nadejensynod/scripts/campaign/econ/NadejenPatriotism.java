package sevenmere.nadejensynod.scripts.campaign.econ;

import com.fs.starfarer.api.impl.campaign.econ.BaseMarketConditionPlugin;
import com.fs.starfarer.api.impl.campaign.econ.ConditionData;
import java.util.Arrays;


public class NadejenPatriotism extends BaseMarketConditionPlugin {

	private static final String [] SynodFactions = new String [] {
		"nadejensynod","nadejensynod_tanjanfellowship",
	};

    /**
     *
     * @param id
     */
    @Override
	public void apply(String id) {
		if (Arrays.asList(SynodFactions).contains(market.getFactionId())) {
			market.getStability().modifyFlat(id, ConditionData.STABILITY_LUDDIC_MAJORITY_BONUS, "Tanjan Fellowship");
		} else {
			market.getStability().modifyFlat(id, ConditionData.STABILITY_LUDDIC_MAJORITY_PENALTY, "Tanjan Insurgency");
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
