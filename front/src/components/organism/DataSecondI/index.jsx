import BarChartExport from "./../../molecules/dataSecondI/ExportTop5/index";
import BarChartImport from "./../../molecules/dataSecondI/ImportTop5/index";
import LineChartTrend from "./../../molecules/dataSecondI/LineGraph/index";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { useRecoilState, useRecoilValue } from "recoil";
import {
  data2ImgAtom,
  pdfStateAtom,
  data2StateAtom,
} from "../../../states/recoilPdfState";
import html2canvas from "html2canvas";
import { excelStateI2 } from "../../../states/Excel";

// function DataSecond() {
//   const params = useParams();

//   let firstExportData
//   let Top5Data

//   // const [currentState, chagneState] = useState([0, 0, '모든 품목', '수출', firstExportData]) // [Export, Import] clicked???

//   useEffect(() => {
//     axios
//       .get(
//         "https://98320413-724a-44ba-a0b5-9b226001b6d6.mock.pstmn.io/api/trade/country/data2?" +
//           "statcd=" + params.nationCode + "&" +
//           "startDate=" + params.duration.substr(0,6) + "&" +
//           "endDate=" + params.duration.substr(7,12)
//       )
//       .then((response) => {
//         firstExportData = response.data.expdlrChange
//         console.log("0000000" + typeof(firstExportData))
//         Top5Data = { 수출: response.data.exportTop, 수입: response.data.importTop }
//         console.log(Top5Data)
//       })
//     }, [params]);

//     const [currentState, chagneState] = useState([0, 0, '모든 품목', '수출', firstExportData]) // [Export, Import] clicked???
//     console.log(currentState)

//   const onChangeExportClick = (item) => {
//     chagneState([1, 0, item, '수출', Top5Data['수출'][item]])
//   }

//   const onChangeImportClick = (item) => {
//     chagneState([0, 1, item, '수입', Top5Data['수입'][item]])
//   }

//   return (
//     <div className="flex justify-around space-x-5 top-0 border-8 border--400">
//       <LineChartTrend anyItem={currentState} />
//       <BarChartExport alreadyClicked={currentState} onSaveClickOrNot={onChangeExportClick} />
//       <BarChartImport alreadyClicked={currentState} onSaveClickOrNot={onChangeImportClick} />
//     </div>
//   );
// }

// export default DataSecond;

function DataSecondI() {
  const [excelData, setExcelData] = useRecoilState(excelStateI2);
  const [data2Img, setData2Img] = useRecoilState(data2ImgAtom);
  const [data2State, setData2State] = useRecoilState(data2StateAtom);
  const pdfState = useRecoilValue(pdfStateAtom);
  const params = useParams();
  const [currentState, changeState] = useState([
    0,
    0,
    "전세계",
    "수출",
    [],
    {},
    "전세계",
    {},
  ]); // initialize the state with an empty array
  let nationConnection;

  useEffect(() => {
    if (pdfState === true) {
      const input = document.getElementById("data2ImgHandler");
      html2canvas(input).then((canvas) => {
        let data2 = canvas.toDataURL("image/png");
        setData2Img(data2);
        setData2State(true);
      });
    }
  }, [pdfState]);

  useEffect(() => {
    axios
      .get(
        "https://ssafycnt.site:8000/ssafycnt-trade-service/api/trade/item/tworow?" +
          "item=" +
          params.hsCode +
          "&" +
          "startDate=" +
          params.duration.substring(0, 6) +
          "&" +
          "endDate=" +
          params.duration.substring(7, 13)
      )
      .then((response) => {
        const firstExportData = response.data.expdlrChange;
        const Top5Data = {
          수출: response.data.exportTop,
          수입: response.data.importTop,
        };
        const nation = response.data.nationName;
        nationConnection = response.data.nationName;
        changeState([
          0,
          0,
          "전세계",
          "수출",
          firstExportData,
          Top5Data,
          nation,
          firstExportData,
        ]);
        setExcelData(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [params]);

  const onChangeExportClick = (item) => {
    changeState([
      1,
      0,
      currentState[5]["수출"][item]["nationName"],
      "수출",
      currentState[4],
      currentState[5],
      currentState[6],
      currentState[5]["수출"][item]["exportChange"],
    ]);
  };
  const onChangeImportClick = (item) => {
    changeState([
      0,
      1,
      currentState[5]["수입"][item]["nationName"],
      "수입",
      currentState[4],
      currentState[5],
      currentState[6],
      currentState[5]["수입"][item]["importChange"],
    ]);
  };

  // // TextMining 화면 캡쳐
  // const [data2Img, setData2Img] = useRecoilState(data2ImgAtom);
  // useEffect(() => {
  //   const input = document.getElementById("data2ImgHadler");
  //   html2canvas(input).then((canvas) => {
  //     const data2 = canvas.toDataURL("image/png");
  //     setData2Img(data2);
  //   });
  // }, [currentState]);
  // // console.log(data2Img);

  return (
    <div className="flex justify-center space-x-5 mt-7" id="data2ImgHandler">
      <LineChartTrend anyItem={currentState} />
      <BarChartExport
        alreadyClicked={currentState}
        onSaveClickOrNot={onChangeExportClick}
      />
      <BarChartImport
        alreadyClicked={currentState}
        onSaveClickOrNot={onChangeImportClick}
      />
    </div>
  );
}

export default DataSecondI;
