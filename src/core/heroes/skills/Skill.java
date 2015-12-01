package core.heroes.skills;

public interface Skill
{
	public boolean onBeginningStage();
	public boolean onDecisionStage();
	public boolean onCardDrawingStage();
	public boolean onPlayingStageBegin();
	
	public String getName();
	public String getDescription();
}
