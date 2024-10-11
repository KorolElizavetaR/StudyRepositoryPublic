package koroler.spring.library.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

// все равно не работает ((
@Aspect
public class BookAspect {
	@Pointcut ("execution(* koroler.spring.library.controllers.BooksController.GetBooks(..))")
	private void PointcutForGetBooks(){};
	
	@Before ("PointcutForGetBooks()")
	public void AdviceThatChangesLikeIntoNull(JoinPoint jPoint)
	{
		Object [] args = jPoint.getArgs();
		String likeArg = (String) args[1];
		if (likeArg != null && (likeArg.isEmpty() || likeArg.isBlank()))
		{
			args[1] = null;
		}
	}
}
