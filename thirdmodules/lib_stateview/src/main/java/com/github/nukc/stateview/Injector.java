package com.github.nukc.stateview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

class Injector {

    /**
     * When viewParent is ConstraintLayout
     * Change other view's Constraint to root, if the origin Constraint is parent
     * <p>
     * Solve the ClassCastException: constraint-layout version 1.1.3
     * java.lang.ClassCastException: android.widget.FrameLayout$LayoutParams cannot be cast to android.support.constraint.ConstraintLayout$LayoutParams
     * at {@link ConstraintLayout#getTargetWidget(int)}
     * at {@link ConstraintLayout#setChildrenConstraints()}
     * at {@link ConstraintLayout#updateHierarchy()}
     * at {@link ConstraintLayout#onMeasure(int, int)}
     *
     * @param viewParent   injectView's parent
     * @param root         wrapper view, replace injectView
     * @param injectViewId injectView's Id
     */
    public static void changeChildrenConstraints(ViewGroup viewParent, FrameLayout root, int injectViewId) {
        if (viewParent instanceof ConstraintLayout) {
            int rootId = R.id.root_id;
            root.setId(rootId);
            ConstraintLayout rootGroup = ((ConstraintLayout) viewParent);
            for (int i = 0, count = rootGroup.getChildCount(); i < count; i++) {
                View child = rootGroup.getChildAt(i);
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) child.getLayoutParams();
                if (layoutParams.circleConstraint == injectViewId) {
                    layoutParams.circleConstraint = rootId;
                } else {
                    if (layoutParams.leftToLeft == injectViewId) {
                        layoutParams.leftToLeft = rootId;
                    } else if (layoutParams.leftToRight == injectViewId) {
                        layoutParams.leftToRight = rootId;
                    }

                    if (layoutParams.rightToLeft == injectViewId) {
                        layoutParams.rightToLeft = rootId;
                    } else if (layoutParams.rightToRight == injectViewId) {
                        layoutParams.rightToRight = rootId;
                    }

                    if (layoutParams.topToTop == injectViewId) {
                        layoutParams.topToTop = rootId;
                    } else if (layoutParams.topToBottom == injectViewId) {
                        layoutParams.topToBottom = rootId;
                    }

                    if (layoutParams.bottomToTop == injectViewId) {
                        layoutParams.bottomToTop = rootId;
                    } else if (layoutParams.bottomToBottom == injectViewId) {
                        layoutParams.bottomToBottom = rootId;
                    }

                    if (layoutParams.baselineToBaseline == injectViewId) {
                        layoutParams.baselineToBaseline = rootId;
                    }
                }
            }
        }
    }
}
