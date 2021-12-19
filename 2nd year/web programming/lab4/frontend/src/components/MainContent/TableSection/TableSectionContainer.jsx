import { connect } from 'react-redux';
import TableSection from './TableSection'

function mapStateToProps(state) {
    return {
        entries: state.table.entries
    };
}
export default connect(
    mapStateToProps,
    null
)(TableSection);
