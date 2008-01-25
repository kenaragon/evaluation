/**
 * $Id: EvaluationEntityProvider.java 1000 May 28, 2007 12:07:31 AM azeckoski $
 * $URL: https://source.sakaiproject.org/contrib $
 * EvaluationEntityProvider.java - evaluation - May 23, 2007 12:07:31 AM - azeckoski
 **************************************************************************
 * Copyright (c) 2008 Centre for Academic Research in Educational Technologies
 * Licensed under the Educational Community License version 1.0
 * 
 * A copy of the Educational Community License has been included in this 
 * distribution and is available at: http://www.opensource.org/licenses/ecl1.php
 *
 * Aaron Zeckoski (azeckoski@gmail.com) (aaronz@vt.edu) (aaron@caret.cam.ac.uk)
 */

package org.sakaiproject.evaluation.logic.entity;

import org.sakaiproject.entitybroker.entityprovider.EntityProvider;
import org.sakaiproject.evaluation.model.EvalEvaluation;

/**
 * Allows external packages to find out the prefix for the evaluation entity
 * (deals with {@link EvalEvaluation} model class)
 * 
 * @author Aaron Zeckoski (aaronz@vt.edu)
 */
public interface EvaluationEntityProvider extends EntityProvider {
	public final static String ENTITY_PREFIX = "eval-evaluation";
}
