package comp2402a2;

import java.lang.reflect.Array;

public class Factory<T> {
	Class<T> mClass;
	
	public Class<T> type() {
		return mClass;
	}
	
	public Factory(Class<T> newClass) {
		mClass = newClass;
	}
	
	// This method is mainly used throughout the other classes
	@SuppressWarnings({"unchecked"})
	public T[] newArray(int n) {
		return (T[])Array.newInstance(mClass, n);
	}
	
	public T newInstance() {
		T x;
		try {
			x = mClass.newInstance();
		} catch (Exception e) {
			x = null;
		}
		return x;
	}

}