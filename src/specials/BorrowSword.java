package specials;

public class BorrowSword extends Instant
{

	public BorrowSword(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Borrow Sword";
	}

}
