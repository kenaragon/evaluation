/**
 * ScaledUtils.java - evaluation - Feb 19, 2007 11:35:56 AM - antranig
 * $URL: https://source.sakaiproject.org/contrib $
 * $Id: Locator.java 11234 Jan 21, 2008 11:35:56 AM azeckoski $
 **************************************************************************
 * Copyright (c) 2008 Centre for Applied Research in Educational Technologies, University of Cambridge
 * Licensed under the Educational Community License version 1.0
 * 
 * A copy of the Educational Community License has been included in this 
 * distribution and is available at: http://www.opensource.org/licenses/ecl1.php
 *
 * Aaron Zeckoski (azeckoski@gmail.com) (aaronz@vt.edu) (aaron@caret.cam.ac.uk)
 */

package org.sakaiproject.evaluation.tool.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.evaluation.model.EvalScale;
import org.sakaiproject.evaluation.model.constant.EvalConstants;
import org.sakaiproject.evaluation.tool.EvaluationConstant;

import uk.org.ponder.stringutil.StringUtil;

/**
 * Utilities for manipulating scales.
 * 
 * @author Antranig Basman (amb26@ponder.org.uk)
 * @author Aaron Zeckoski (aaron@caret.cam.ac.uk)
 */
public class ScaledUtils {

   private static Log log = LogFactory.getLog(ScaledUtils.class);

	public static String[] idealKeys = {
		EvalConstants.SCALE_IDEAL_NONE,
		EvalConstants.SCALE_IDEAL_LOW, 
		EvalConstants.SCALE_IDEAL_MID, 
		EvalConstants.SCALE_IDEAL_HIGH, 
		EvalConstants.SCALE_IDEAL_OUTSIDE};

	public static String[] startColours = {
		EvaluationConstant.BLUE_COLOR, 
		EvaluationConstant.GREEN_COLOR,
		EvaluationConstant.RED_COLOR,
		EvaluationConstant.RED_COLOR,
		EvaluationConstant.GREEN_COLOR};

	public static String[] endColours = {
		EvaluationConstant.BLUE_COLOR, 
		EvaluationConstant.RED_COLOR,
		EvaluationConstant.RED_COLOR,
		EvaluationConstant.GREEN_COLOR,
		EvaluationConstant.GREEN_COLOR};

	public static int idealIndex(EvalScale scale) {
      int index = -1;
      for (int i = 0; i < idealKeys.length; ++ i) {
         if (StringUtil.equals(scale.getIdeal(), idealKeys[i])) {
            index = i;
            break;
         }
      }
      if (index == -1) {
         // Fix for http://www.caret.cam.ac.uk/jira/browse/CTL-562 - added to ensure this will not cause a failure
         log.warn("Could not find index for scale ("+scale.getId()+") for ideal setting: " + scale.getIdeal() + ", setting to default of 0 (no ideal)");
         index = 0;
      }
      return index;
	}

	public static String getIdealImageURL(EvalScale scale) {
		return EvaluationConstant.COLORED_IMAGE_URLS[idealIndex(scale)];
	}

	public static Color getStartColor(EvalScale scale) {
		return Color.decode(startColours[idealIndex(scale)]);
	}

	public static Color getEndColor(EvalScale scale) {
		return Color.decode(endColours[idealIndex(scale)]);
	}

	/**
	 * Produce scale labels for a list of scales that can be used in a pulldown menu
	 * 
	 * @param scales a list of {@link EvalScale}
	 * @return an array of labels for the passed in scales
	 */
	public static String[] getScaleLabels(List<EvalScale> scales) {
		List<String> scaleLabels = new ArrayList<String>();
		for (int i = 0; i < scales.size(); i++) {
			EvalScale scale = scales.get(i);
			String scaleOptionsStr = "";
			String[] scaleOptionsArr = scale.getOptions();
			for (int j = 0; j < scaleOptionsArr.length; j++) {
				if (scaleOptionsStr.equals("")) {
					scaleOptionsStr = scaleOptionsArr[j];
				} else {
					scaleOptionsStr = scaleOptionsStr + ", " + scaleOptionsArr[j];
				}
			}
			scaleLabels.add(scaleOptionsArr.length + " pt - " + scale.getTitle() + " (" + scaleOptionsStr + ")");
		}
		return (String[]) scaleLabels.toArray(new String[] {});
	}

	/**
	 * Produce values for a list of scales that can be used in a pulldown menu
	 * 
	 * @param scales a list of {@link EvalScale}
	 * @return an array of values for the passed in scales
	 */
	public static String[] getScaleValues(List<EvalScale> scales) {
		List<String> scaleValues = new ArrayList<String>();
		for (int i = 0; i < scales.size(); i++) {
			EvalScale scale = scales.get(i);
			scaleValues.add(scale.getId().toString());
		}
		return (String[]) scaleValues.toArray(new String[] {});
	}

}
