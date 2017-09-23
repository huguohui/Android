package com.downloader.engine;

/**
 * Worker is running on child thread for do somthing.
 */
public interface Worker extends Runnable {
	/**
	 * Add a workable to working.
	 * @param workable For working.
	 */
	void add(Workable workable);


	/**
	 * Remove a workable.
	 * @param workable Will to removing.
	 */
	Workable remove(Workable workable);


	/**
	 * Get thread of this worker.
	 * @return A thread of this worker.
	 */
	Thread thread();


	/**
	 * Listener for listening worker state.
	 */
	interface OnWorkDoneListener {
		/**
		 * Invokes on work done.
		 * @param w {@lik Worker}.
		 */
		void onWorkDone(Worker w);
	}

	class RunnableAdapter implements Runnable {
		Workable workable;

		RunnableAdapter(Workable w) {
			workable = w;
		}


		@Override
		public void run() {
			try {
				if (workable != null)
					workable.work();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
