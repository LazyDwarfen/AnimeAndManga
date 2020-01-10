import React from 'react';
import MangaService from './services/MangaService';
import AnimeService from './services/AnimeService';
import StudioService from './services/StudioService';
import AnimeTab from './components/AnimeTabComponent';
import MangaTab from './components/MangaTabComponent';
import StudioTab from './components/StudioTabComponent';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Select from 'react-select';
import 'bootstrap/dist/css/bootstrap.min.css';

import './App.css';

class App extends React.Component {
  
  constructor(props) {
    super(props);
    this.handleCloseModal = this.handleCloseModal.bind(this);
    this.handleShowModal = this.handleShowModal.bind(this);
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSelectStudioChange = this.handleSelectStudioChange.bind(this);
    this.handleSelectMangaChange = this.handleSelectMangaChange.bind(this);
    this.updateAll = this.updateAll.bind(this);

    this.state = {
      showModal: false,
      disableButtons: false,
      modalData: {}
    };

    this.updateAll();
  }

  updateAll() {
    AnimeService.getAll().then(res => {
        if (res.status === 'OK')
          this.setState((state) => state.animeData = res.data);
        else
          alert(res.status);
      },
      () => {
        alert('error');
      }
    );
    MangaService.getAll().then(res => {
        if (res.status === 'OK') {
          this.setState((state) => state.mangaData = res.data);
          this.setState((state) => state.mangaSelectList = res.data.map(item => {
            return {
              value: item.id,
              label: item.name
            };
          }));
        } else
          alert(res.status);
      },
      () => {
        alert('error');
      }
    );
    StudioService.getAll().then(res => {
        if (res.status === 'OK') {
          this.setState((state) => state.studioData = res.data);
          this.setState((state) => state.studioSelectList = res.data.map(item => {
            return {
              value: item.id,
              label: item.name
            };
          }));
        } else
          alert(res.status);
      },
      () => {
        alert('error');
      }
    );
  }
  
  handleCloseModal() {
    this.setState((state) => state.showModal = false);
  }

  handleSaveDataOnModal(e, createType, dataType, data) {
    function afterGetErrorOnRequest(response, context) {
      if (response.status === 'OK') {
        context.setState((state) => state.disableButtons = false);
        context.setState((state) => state.showModal = false);
        context.updateAll();
      } else {
        alert(response.status);
        context.setState((state) => state.disableButtons = true);
      }
    }

    function afterSuccessOnRequest(error, context) {
      alert('error');
      context.setState((state) => state.disableButtons = true);
      console.log(error);
    }

    if (data.name == null || data.name === '') {
      alert('\"Name\" is a necessary field');
      return;
    }
    this.setState((state) => state.disableButtons = true);
    let currentService;
    switch (dataType) {
      case 'studio': {
        currentService = StudioService;
        break;
      }
      case 'anime': {
        currentService = AnimeService;
        if (data.manga == ''|| data.studio == undefined)
          data.manga = null;
        else
          data.manga = {
            id: data.manga.value
          };
        if (data.studio == ''|| data.studio == undefined)
          data.studio = null;
        else
          data.studio = {
            id: data.studio.value
          };
        break;
      }
      case 'manga': {
        currentService = MangaService;
        break;
      }
    }
    if (createType)
      currentService.create(data).then(
        (response) => afterGetErrorOnRequest(response, this),
        (error) => afterSuccessOnRequest(error, this)
      );
    else
      currentService.update(data).then(
        (response) => afterGetErrorOnRequest(response, this),
        (error) => afterSuccessOnRequest(error, this)
      );
    }

  handleInputChange(event) {
    const target = event.target;
    let value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    this.setState((state)=>{
      state.modalData[name] = value;
    });
  }

  handleSelectStudioChange(newValue) {
    this.setState((state)=>{
      state.modalData.studio = newValue;
    });
  }
  
  handleSelectMangaChange(newValue) {
    this.setState((state)=>{
      state.modalData.manga = newValue;
    });
  }

  handleShowModal(e, dataType, creatingModalFormType, data){
    this.setState((state) => state.showModal = true);
    this.setState((state) => state.creatingModalFormType = creatingModalFormType);
    this.setState((state) => state.modalType = dataType);
    this.setState((state) => state.modalData = data==undefined? {} : data);
  }
  
  render() {
    const animeList = this.state.animeData;
    const mangaList = this.state.mangaData;
    const studioList = this.state.studioData;
    const selectedStudio = this.state.modalData.studio;
    const selectedManga = this.state.modalData.manga;

    console.log(this.state);
    if(animeList===undefined||
      mangaList===undefined||
      studioList===undefined){
      return (<h1 style={{
        width: '300px',
        margin: '30vh auto'}}>
          Loading
        </h1>);
    }
    return (
      <div style={{
        display: 'grid',
        gridTemplateColumns: '1fr 1fr 1fr',
        gridTemplateRows: '60px 1fr',
        width: '700px',
        margin: 'auto'
        }}>
        <div><h1>Anime</h1></div>
        <div><h1>Manga</h1></div>
        <div><h1>Studio</h1></div>
        <div><AnimeTab animeList={{animeList}} handleShowModal={this.handleShowModal} updateFunction={this.updateAll}/></div>
        <div><MangaTab mangaList={{mangaList}} handleShowModal={this.handleShowModal} updateFunction={this.updateAll}/></div>
        <div><StudioTab studioList={{studioList}} handleShowModal={this.handleShowModal} updateFunction={this.updateAll}/></div>

        <Modal show={this.state.showModal} onHide={this.handleCloseModal}>
          <Modal.Header closeButton>
            <Modal.Title>{`${(this.state.creatingModalFormType ? 'Creating' : 'Editing')} ${this.state.modalType} block`}</Modal.Title>
          </Modal.Header>
          <Modal.Body>
                <div className = "Name" >
                  Name: <input
                          name="name"
                          type="text"
                          value={this.state.modalData.name}
                          onChange={this.handleInputChange} />
                </div> 
                <div className = "Genre" style= {{display: (this.state.modalType == 'anime'||this.state.modalType=='manga' ? 'block' : 'none')}} >
                  Genre: <input
                          name="genre"
                          type="text"
                          value={this.state.modalData.genre}
                          onChange={this.handleInputChange} />
                </div> 
                <div className = "Ongoing" style= {{display: (this.state.modalType == 'anime' ? 'block' : 'none')}} >
                  Ongoing: <input
                          name="ongoing"
                          type="checkbox"
                          checked={this.state.modalData.ongoing}
                          onChange={this.handleInputChange} />
                </div> 
                <div className = "Studio" style= {{display: (this.state.modalType == 'anime' ? 'block' : 'none')}} >
                  Studio: <Select
                          value={selectedStudio}
                          onChange={this.handleSelectStudioChange} 
                          options={this.state.studioSelectList}/>
                </div> 
                <div className = "Manga" style= {{display: (this.state.modalType == 'anime' ? 'block' : 'none')}} >
                  Manga: <Select
                          value={selectedManga}
                          onChange={this.handleSelectMangaChange} 
                          options={this.state.mangaSelectList}/>
                </div> 
                <div className = "Author" style= {{display: (this.state.modalType=='manga' ? 'block' : 'none')}} >
                  Author: <input
                          name="author"
                          type="text"
                          value={this.state.modalData.author}
                          onChange={this.handleInputChange} />
                </div>
                <div className = "Pic" style= {{display: (this.state.modalType == 'anime'||this.state.modalType=='manga' ? 'block' : 'none')}} >
                  PicURL: <input
                          name="picURL"
                          type="text"
                          value={this.state.modalData.picURL}
                          onChange={this.handleInputChange} />
                </div> 
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={this.handleCloseModal}>
              Cancel
            </Button>
            <Button variant="primary" onClick={(e)=>this.handleSaveDataOnModal(e, this.state.creatingModalFormType, this.state.modalType, this.state.modalData)}>
              Ok
            </Button>
          </Modal.Footer>
      </Modal>
      </div>
    );
  }
}

export default App;
