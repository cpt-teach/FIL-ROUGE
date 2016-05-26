	package fr.uv1.utils;

	import java.util.*;

	/**
	 * 
	 * @author prou
	 * 
	 */
	public class MyCalendar extends GregorianCalendar {

		private static final long serialVersionUID = 1L;

		private static MyCalendar virtualCalendar = null;

		/**
		 * to change the virtual date
		 * 
		 * @param day
		 *            (from 1 to 31)
		 * @param month
		 *            (from 1 to 12)
		 * @param year
		 *            (from 1 to ...)
		 * @param hour
		 *            (from 0 to 23)
		 * @param minute
		 *            (from 0 to 59)
		 */
		public static void setDate(int year, int month, int day, int hour, int minute) {
			virtualCalendar = new MyCalendar(year, month, day, hour, minute);
		}

		/**
		 * to change the virtual date
		 * 
		 * @param day
		 *            (from 1 to 31)
		 * @param month
		 *            (from 1 to 12)
		 * @param year
		 *            (from 1 to ...)
		 */
		public static void setDate(int year, int month, int day) {
			virtualCalendar = new MyCalendar(year, month, day, 0, 0);
		}

		/**
		 * to change the virtual date
		 * 
		 * @param date
		 *            virtual date
		 */
		public static void setDate(MyCalendar date) {
			virtualCalendar = new MyCalendar(date.get(Calendar.YEAR),
					date.get(Calendar.MONTH) + 1, date.get(Calendar.DAY_OF_MONTH),
					date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE));
		}

		/**
		 * to change to the "real date"
		 */
		public static void setDate() {
			virtualCalendar = null;
		}

		/**
		 * To obtain a new object MyCalendar corresponding either to the real date or the virtual one
		 * 
		 * @return a MyCalendar object corresponding to the virtual or the real date
		 * 
		 */
		public static MyCalendar getDate() {
			if (virtualCalendar == null) {
				return new MyCalendar(new GregorianCalendar());
			} else {
				return new MyCalendar(virtualCalendar);
			}
		}

		/**
		 * create a MyCalendar object according to the parameters
		 * 
		 * @param day
		 *            (from 1 to 31)
		 * @param month
		 *            (from 1 to 12)
		 * @param year
		 *            (from 1 to ...)
		 * @param hour
		 *            (from 0 to 23)
		 * @param minute
		 *            (from 0 to 59)
		 */
		public MyCalendar(int year, int month, int day, int hour, int minute) {
			super(year, month - 1, day, hour, minute);
			if (get(Calendar.YEAR) != year)
				throw new IllegalArgumentException();
			if (get(Calendar.MONTH) != (month - 1))
				throw new IllegalArgumentException();
			if (get(Calendar.DAY_OF_MONTH) != day)
				throw new IllegalArgumentException();
			if (get(Calendar.HOUR_OF_DAY) != hour)
				throw new IllegalArgumentException();
			if (get(Calendar.MINUTE) != minute)
				throw new IllegalArgumentException();
		}

		/**
		 * create a MyCalendar object according to the parameters
		 * 
		 * @param day
		 *            (from 1 to 31)
		 * @param month
		 *            (from 1 to 12)
		 * @param year
		 *            (from 1 to ...)
		 */
		public MyCalendar(int year, int month, int day) {
			this(year, month, day, 0, 0);
		}

		/**
		 * Creates a MyCalendar corresponding to the parameter MyCalendar 
		 * 
		 * @param date
		 *            : date to clone
		 */
		public MyCalendar(MyCalendar date) {
			super(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date
					.get(Calendar.DAY_OF_MONTH), date.get(Calendar.HOUR_OF_DAY),
					date.get(Calendar.MINUTE), date.get(Calendar.SECOND));
		}

		/**
		 * Creates a MyCalendar corresponding to the parameter GregorianCalendar 
		 * 
		 * @param date
		 *            : date to clone
		 */
		private MyCalendar(GregorianCalendar date) {
			super(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date
					.get(Calendar.DAY_OF_MONTH), date.get(Calendar.HOUR_OF_DAY),
					date.get(Calendar.MINUTE), date.get(Calendar.SECOND));
		}

		/**
		 * returns true if MyCalendar is in the past in the virtual date
		 */
		public boolean isInThePast() {
			return this.before(getDate());
		}

		/**
		   */
		public static MyCalendar fromString(String date) {
			int hour = 0;
			int minute = 0;
			int second = 0;
			int day = Integer.parseInt(date.substring(8,10));
			int mounth = Integer.parseInt(date.substring(5,7));
			int year = Integer.parseInt(date.substring(0,4));
			if (date.length() > 20){
			hour = Integer.parseInt(date.substring(14,16));
			minute = Integer.parseInt(date.substring(17,19));
			second = Integer.parseInt(date.substring(20,22));}
			MyCalendar newdate = new MyCalendar(year, mounth, day, hour,minute); //might add mounth -1 after test
			return newdate;
					
		}
		
		public String toString() {
			String s = "" + get(Calendar.DAY_OF_MONTH) + "/";
			s += (get(Calendar.MONTH) + 1) + "/";
			s += get(Calendar.YEAR) + "   ";
			s += get(Calendar.HOUR_OF_DAY) + " h ";
			s += get(Calendar.MINUTE) + " mn  ";
			s += get(Calendar.SECOND) + " sec";
			return s;
		}


		public String toString2() {
			String s = get(Calendar.YEAR) + "-";
			s += (get(Calendar.MONTH) + 1) + "-";
			s +=  get(Calendar.DAY_OF_MONTH);
			return s;
		}
	}


