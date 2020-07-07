package five.miles.failurism;

import android.content.Context;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Made by "s.maks" of StackOverflow
 * Commented by "s.maks" and Ralf van Aert
 */

public class GridAutofitLayoutManager extends GridLayoutManager
{
    private int columnWidth;
    private boolean isColumnWidthChanged = true;
    private int lastWidth;
    private int lastHeight;

    /**
     * GridAutofitLayoutManager (Constructor)
     * initializes GridAutoFitLayoutManager getting context and columnWidth.
     * @param context
     * @param columnWidth
     */

    public GridAutofitLayoutManager(@NonNull final Context context, final int columnWidth) {
        /* Initially set spanCount to 1, will be changed automatically later. */
        super(context, 1);
        setColumnWidth(checkedColumnWidth(context, columnWidth));
    }

    public GridAutofitLayoutManager(
            @NonNull final Context context,
            final int columnWidth,
            final int orientation,
            final boolean reverseLayout) {

        /* Initially set spanCount to 1, will be changed automatically later. */
        super(context, 1, orientation, reverseLayout);
        setColumnWidth(checkedColumnWidth(context, columnWidth));
    }

    private int checkedColumnWidth(@NonNull final Context context, int columnWidth) {
        if (columnWidth <= 0) {
            columnWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48,
                    context.getResources().getDisplayMetrics());
        }
        return columnWidth;
    }

    /**
     * setColumnWidth
     * Sets new column width for GridAutofitLayoutManager.
     * @param newColumnWidth
     */
    public void setColumnWidth(final int newColumnWidth) {
        if (newColumnWidth > 0 && newColumnWidth != columnWidth) {
            columnWidth = newColumnWidth;
            isColumnWidthChanged = true;
        }
    }

    /**
     * onLayoutChildren
     * Changes spanCount, amount of columns based on space, padding and width or height.
     * @param recycler
     * @param state
     */
    @Override
    public void onLayoutChildren(@NonNull final RecyclerView.Recycler recycler, @NonNull final RecyclerView.State state) {
        final int width = getWidth();
        final int height = getHeight();
        if (columnWidth > 0 && width > 0 && height > 0 && (isColumnWidthChanged || lastWidth != width || lastHeight != height)) {
            final int totalSpace;
            if (getOrientation() == VERTICAL) {
                totalSpace = width - getPaddingRight() - getPaddingLeft();
            } else {
                totalSpace = height - getPaddingTop() - getPaddingBottom();
            }
            final int spanCount = Math.max(1, totalSpace / columnWidth);
            setSpanCount(spanCount);
            isColumnWidthChanged = false;
        }
        lastWidth = width;
        lastHeight = height;
        super.onLayoutChildren(recycler, state);
    }
}