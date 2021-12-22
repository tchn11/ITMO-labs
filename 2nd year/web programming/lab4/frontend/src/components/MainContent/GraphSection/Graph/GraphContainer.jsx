import { connect } from 'react-redux';
import Graph from './Graph'
import {
    changeX,
    changeY,
    checkEntry,
} from '../../../../redux/modules/values';

function mapStateToProps(state) {
    return {
        rMin: state.values.rMin,
        rMax: state.values.rMax,
        rCurrent: state.values.rCurrent,
        xMin: state.values.xMin,
        xMax: state.values.xMax,
        xCurrent: state.values.xCurrent,
        yMin: state.values.yMin,
        yMax: state.values.yMax,
        yCurrent: state.values.yCurrent,
        entries: state.table.entries
    };
}

function mapDispatchToProps(dispatch) {
    return {
        changeX: (value) => dispatch(changeX(value)),
        changeY: (value) => dispatch(changeY(value)),
        checkEntry: () => dispatch(checkEntry())
    };
}

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(Graph);
