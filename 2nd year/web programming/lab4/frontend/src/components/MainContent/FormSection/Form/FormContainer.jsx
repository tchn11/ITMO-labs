import { connect } from 'react-redux';
import Form from './Form'
import {
    changeR,
    changeX,
    changeY,
    checkEntry,
    clearEntries
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
        yCurrent: state.values.yCurrent
    };
}

function mapDispatchToProps(dispatch) {
    return {
        changeR: (value) => dispatch(changeR(value)),
        changeX: (value) => dispatch(changeX(value)),
        changeY: (value) => dispatch(changeY(value)),
        checkEntry: () => dispatch(checkEntry()),
        clearEntries: () => dispatch(clearEntries())
    };
}

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(Form);