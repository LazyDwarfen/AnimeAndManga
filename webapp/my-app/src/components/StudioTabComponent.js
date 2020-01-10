import React from 'react';

import StudioBlock from './StudioBlockComponent';
import Button from 'react-bootstrap/Button';

class StudioTabComponent extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        console.log(this.props.studioList.studioList);
        return( 
            <div style={{display: 'grid'}}>
                <Button variant="secondary" className="add" onClick={(e)=>{this.props.handleShowModal(e, 'studio', true)}}>add</Button>
                {this.props.studioList.studioList.map((item) => (
                    <StudioBlock key={(item.id)} studio={item} updateFunction={this.props.updateFunction} showModalFunction={this.props.handleShowModal} />
                ))}
            </div>
        );
    }
}
export default StudioTabComponent;   