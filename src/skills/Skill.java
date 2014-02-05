package core;

public interface Skill
{
	public boolean onBeginningStage();
	public boolean onDecisionStage();
	public boolean onCardDrawingStage();
	public boolean onPlayingStageBegin();
}
