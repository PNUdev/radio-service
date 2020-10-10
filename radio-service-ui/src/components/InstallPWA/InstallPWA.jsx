import React from 'react';

import { Button, Modal, Card, CardText, CardBody, CardTitle } from 'reactstrap';

import useIosInstallPrompt from '../../pwa_utils/useIosInstallPrompt';
import useWebInstallPrompt from '../../pwa_utils/useWebInstallPrompt';

import pwaLoad from '../../images/pwa-load.png'

const InstallPWA = () => {
  const [iosInstallPrompt, handleIOSInstallDeclined] = useIosInstallPrompt();
  const [webInstallPrompt, handleWebInstallDeclined, handleWebInstallAccepted] = useWebInstallPrompt();

  if (!iosInstallPrompt && !webInstallPrompt) return null;

  return (
    <Modal isOpen centered>
      <Card>
        <img
          className="mx-auto"
          style={{
            borderTopRightRadius: '50%',
            borderTopLeftRadius: '50%',
            backgroundColor: '#fff',
            marginTop: '-50px'
          }}
          width="100px"
          src={pwaLoad}
          alt="Icon"
        />
        <CardBody>
          <CardTitle className="text-center">
            <h3>Встановити</h3>
          </CardTitle>
          {iosInstallPrompt && (
            <>
              <CardText className="text-center">
                Натисніть
                <img
                  src="https://developer.apple.com/design/human-interface-guidelines/ios/images/icons/navigation_bar_toobar_icons/Navigation_Action_2x.png"
                  style={{ margin: 'auto 8px 8px' }}
                  className=""
                  alt="Add to homescreen"
                  width="20"
                />
                а потім &quot;Добавити на робочий стіл&quot;
              </CardText>

              <div className="d-flex justify-content-center">
                <Button onClick={handleIOSInstallDeclined}>Close</Button>
              </div>
            </>
          )}
          {webInstallPrompt && (
            <div className="d-flex justify-content-around">
              <Button color="primary" onClick={handleWebInstallAccepted}>
                Встановити
              </Button>

              <Button onClick={handleWebInstallDeclined}>Запитати пізніше</Button>
            </div>
          )}
        </CardBody>
      </Card>
    </Modal>
  );
};

export default InstallPWA;
