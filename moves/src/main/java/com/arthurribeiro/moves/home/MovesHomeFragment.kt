package com.arthurribeiro.moves.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.arthurribeiro.foundation.utils.IntentHelper.Companion.MOVE_URL
import com.arthurribeiro.foundation.R as foundationRes
import com.arthurribeiro.foundation.utils.firstCharUpperCase
import com.arthurribeiro.foundation.utils.toPercent
import com.arthurribeiro.foundation.views.Modal
import com.arthurribeiro.model.model.MoveDetailDTO
import com.arthurribeiro.moves.MovesViewModel
import com.arthurribeiro.moves.R
import com.arthurribeiro.moves.databinding.FragmentMovesHomeBinding
import com.arthurribeiro.moves.databinding.InfoModalBinding
import com.arthurribeiro.moves.databinding.PokemonsAccordionBinding
import com.arthurribeiro.network.network.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovesHomeFragment : Fragment() {

    private var _binding: FragmentMovesHomeBinding? = null
    private val binding get() = _binding!!

    private var _accordionBinding: PokemonsAccordionBinding? = null
    private val accordionBinding get() = _accordionBinding!!

    private val viewModel: MovesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovesHomeBinding.inflate(inflater, container, false)
        _accordionBinding = PokemonsAccordionBinding.inflate(inflater, null, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString(MOVE_URL)
        with(viewModel) {
            setMoveUrl(url)
            getMoveUrl()?.let { viewModel.getMoveDetail(it) }
        }
        setObservers()
        setActions()
        setModals()
        setAccordion()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setObservers() {
        viewModel.moveDetail.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> showProgressBar()
                is Resource.Success -> {
                    dismissProgressBar()
                    setViews(it.data)
                }
                is Resource.Error.NoConnection -> {
                    dismissProgressBar()
                    handleErrorFloater(foundationRes.string.no_connection_error_message)
                }
                else -> {
                    dismissProgressBar()
                    handleErrorFloater(foundationRes.string.generic_error_message)
                }
            }
        }
    }

    private fun setActions() {
        with(binding) {
            movesHomeHeader.setOnRightIconClickListener { requireActivity().finish() }
        }
    }

    private fun setViews(move: MoveDetailDTO) {
        with(binding) {
            movesHomeTitle.text = (move.name ?: "-").firstCharUpperCase()
            movesHomeSubtitle.text = move.effectEntries?.first()?.effect?.replace(
                "\$effect_chance",
                move.effectChance.toString()
            )
            movesAccuracy.text = (move.accuracy ?: 0).toPercent()
            movesDamageType.text = (move.damageClass?.name ?: "-").firstCharUpperCase()
            movesIntroducedIn.text = (move.generation?.name ?: "-").firstCharUpperCase()
            movesAilmentInflicted.text = (move.meta?.ailment?.name ?: "-").firstCharUpperCase()
            movesPower.text = (move.power ?: "-").toString()
            movesPowerPoints.text = (move.pp ?: "-").toString()
            movesTarget.text = (move.target?.name?.replace("-", " ") ?: "-").firstCharUpperCase()
            movesElementalType.text = (move.type?.name ?: "-").firstCharUpperCase()
            movesCriticalRate.text = (move.meta?.criticalRate ?: "-").toString()
            move.meta?.drain?.let {
                if (it > 0) movesDrainRecoilLabel.viewText = getString(R.string.drain_label)
                else movesDrainRecoilLabel.viewText = getString(R.string.recoil_label)

                movesDrainRecoil.text = it.toString()
            }
            movesFlinchChance.text = (move.meta?.flinchChance ?: "-").toString()
            movesHealing.text = (move.meta?.healing ?: "-").toString()
            movesMinimumHits.text = (move.meta?.minHits ?: "-").toString()
            movesMaximumHits.text = (move.meta?.maxHits ?: "-").toString()
            movesMinimumTurns.text = (move.meta?.minTurns ?: "-").toString()
            movesMaximumTurns.text = (move.meta?.maxTurns ?: "-").toString()
            movesStatChance.text = (move.meta?.statChance ?: "-").toString()
        }
    }

    private fun setAccordion(){
        binding.movesAccordion.apply {
            addView(accordionBinding.root)
        }
    }

    private fun setModals() {
        with(binding) {
            movesAccuracyLabel.setOnIconClick {
                showModal(
                    getString(R.string.moves_accuracy_label).replace(":", ""),
                    getString(R.string.moves_accuracy_modal_description),
                    "accuracy"
                )
            }
            movesDamageTypeLabel.setOnIconClick {
                showModal(
                    getString(R.string.moves_damage_type_label).replace(":", ""),
                    getString(R.string.moves_damage_type_modal_description),
                    "damage_type"
                )
            }
            movesIntroducedInLabel.setOnIconClick {
                showModal(
                    getString(R.string.moves_generation_introduced_label).replace(":", ""),
                    getString(R.string.moves_generation_introduced_modal_description),
                    "introduced_in"
                )
            }
            movesAilmentInflictedLabel.setOnIconClick {
                showModal(
                    getString(R.string.moves_ailment_inflicted_label).replace(":", ""),
                    getString(R.string.moves_ailment_inflicted_modal_description),
                    "ailment_inflicted"
                )
            }
            movesPowerLabel.setOnIconClick {
                showModal(
                    getString(R.string.moves_power_label).replace(":", ""),
                    getString(R.string.moves_power_modal_Description),
                    "power"
                )
            }
            movesPowerPointsLabel.setOnIconClick {
                showModal(
                    getString(R.string.moves_power_points_label).replace(":", ""),
                    getString(R.string.moves_power_points_modal_description),
                    "power_points"
                )
            }
            movesTargetLabel.setOnIconClick {
                showModal(
                    getString(R.string.moves_target_label).replace(":", ""),
                    getString(R.string.moves_target_modal_description),
                    "target"
                )
            }
            movesElementalTypeLabel.setOnIconClick {
                showModal(
                    getString(R.string.moves_elemental_type_label).replace(":", ""),
                    getString(R.string.moves_elemental_type_modal_description),
                    "elemental_type"
                )
            }
            movesCriticalRateLabel.setOnIconClick {
                showModal(
                    getString(R.string.critical_hit_rate_label).replace(":", ""),
                    getString(R.string.critical_hit_rate_modal_description),
                    "critical_rate"
                )
            }
            movesDrainRecoilLabel.setOnIconClick {
                movesDrainRecoilLabel.viewText?.let { title ->
                    showModal(
                        title.replace(":", ""),
                        getString(R.string.drain_recoil_modal_description),
                        "drain_recoil"
                    )
                }
            }
            movesFlinchChanceLabel.setOnIconClick {
                showModal(
                    getString(R.string.flinch_chance_label).replace(":", ""),
                    getString(R.string.flinch_chance_modal_description),
                    "flinch_chance"
                )
            }
            movesHealingLabel.setOnIconClick {
                showModal(
                    getString(R.string.healing_label).replace(":", ""),
                    getString(R.string.healing_modal_description),
                    "healing"
                )
            }
            movesMinimumHitsLabel.setOnIconClick {
                showModal(
                    getString(R.string.minimum_hits_label).replace(":", ""),
                    getString(R.string.minimum_hits_modal_description),
                    "minimum_hits"
                )
            }
            movesMaximumHitsLabel.setOnIconClick {
                showModal(
                    getString(R.string.maximum_hits_label).replace(":", ""),
                    getString(R.string.maximum_hits_modal_description),
                    "maximum_hits"
                )
            }
            movesMinimumTurnsLabel.setOnIconClick {
                showModal(
                    getString(R.string.minimum_turns_label).replace(":", ""),
                    getString(R.string.minimum_turns_modal_description),
                    "minimum_turns"
                )
            }
            movesMaximumTurnsLabel.setOnIconClick {
                showModal(
                    getString(R.string.maximum_turns_label).replace(":", ""),
                    getString(R.string.maximum_turns_description_modal),
                    "maximum_turns"
                )
            }
            movesStatChanceLabel.setOnIconClick {
                showModal(
                    getString(R.string.stat_chance_label).replace(":", ""),
                    getString(R.string.stat_chance_modal_Description),
                    "stat_chance"
                )
            }
        }
    }

    private fun showModal(title: String, description: String, tag: String) {
        val modalBinding =
            InfoModalBinding.inflate(LayoutInflater.from(context), binding.root, false)
        val modal = Modal(modalBinding.root)

        modalBinding.movesModalTitle.text = title
        modalBinding.movesModalDescription.text = description
        modalBinding.movesModalIcClose.setOnClickListener { modal.dismissAllowingStateLoss() }
        modal.show(childFragmentManager, tag)
    }

    private fun showProgressBar() {
        binding.movesContainer.alpha = 0.5f
        binding.movesProgressBar.isVisible = true
    }

    private fun dismissProgressBar() {
        binding.movesContainer.alpha = 1.0f
        binding.movesProgressBar.isVisible = false
    }

    private fun handleErrorFloater(stringRes: Int) {
        with(binding) {
            errorFloater.isVisible = true
            errorFloater.errorText = getString(stringRes)
            errorFloater.isButtonVisible = true
            errorFloater.setOnTryAgainButtonClick {
                viewModel.getMoveUrl()?.let { url -> viewModel.getMoveDetail(url) }
                errorFloater.isVisible = false
            }
        }
    }

}