/**
 * Copyright (C) 2000-2017 Atomikos <info@atomikos.com>
 *
 * LICENSE CONDITIONS
 *
 * See http://www.atomikos.com/Main/WhichLicenseApplies for details.
 */

package com.atomikos.icatch;


/**
 * 通过线程变量管理 组合事务。可以当做一个CompositeTransaction的存储器
 * 通过事务相关的方法进行增删改查，并不执行业务逻辑
 *
 *
 * This interface outlines the API for managing composite transactions
 * in the local VM.
 */

public interface CompositeTransactionManager
{
	/**
	 * Starts a new (sub)transaction (not an activity) for the current thread.
	 * Associates the current thread with that instance.
	 * <br>
	 * <b>NOTE:</b> subtransactions should not be mixed: either each subtransaction is
	 * an activity, or not (default). Use suspend/resume if mixed models are necessary:
	 * for instance, if you want to create a normal transaction within an activity, then
	 * suspend the activity first before starting the transaction. Afterwards, resume the
	 * activity.
	 *
	 * @param Timeout (in millis) for the transaction.
	 * 
	 * @return CompositeTransaction The new instance.
	 * @exception SysException Unexpected error.
	 * @exception IllegalStateException If there is an existing transaction that is 
	 * an activity instead of a classical transaction.
	 */

	CompositeTransaction createCompositeTransaction ( long timeout ) 
	throws SysException, IllegalStateException;

	/**
	 * @return CompositeTransaction The instance for the current thread, null if none.
	 *
	 * @exception SysException On unexpected failure.
	 */

	 CompositeTransaction getCompositeTransaction () throws SysException;

	/**
	 * Gets the composite transaction with the given id.
	 * This method is useful e.g. for retrieving a suspended 
	 * transaction by its id.
	 *
	 * @param tid The id of the transaction.
	 * @return CompositeTransaction The transaction with the given id,
	 * or null if not found.
	 * @exception SysException Unexpected failure.
	 */

	CompositeTransaction getCompositeTransaction ( String tid )
	throws SysException;

	/**
	 * Re-maps the calling thread to the given transaction.
	 *
	 * @param compositeTransaction
	 * @exception IllegalStateException If this thread has a transaction context already.
	 * @exception SysException 
	 */

	void resume ( CompositeTransaction compositeTransaction )
	throws IllegalStateException, SysException;

	/**
	 * Suspends the transaction context for the current thread.
	 * This method suspends the entire transaction tree, including any parent transactions.
	 *
	 * @return CompositeTransaction The transaction for the current thread.
	 *
	 * @exception SysException 
	 */

	 CompositeTransaction suspend() throws SysException ;

    /**
     * Recreate a composite transaction based on an imported context. Needed by
     * the application's communication layer.
     *
     * @param context
     *            The propagationcontext.
     * @param orphancheck
     *            If true, real composite txs are done. If false, OTS like
     *            behavior applies.
     * @param heur_commit
     *            True for heuristic commit, false for heuristic rollback.
     *
     * @return CompositeTransaction The recreated local instance.
     * @exception SysException
     *                Failure.
     */

	 CompositeTransaction recreateCompositeTransaction(
			Propagation propagation, boolean orphancheck, boolean heur_commit);



}
